package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosCompraIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressosUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosPedidoIngressoDetalhado;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.validacoes.IngressoValidator;
import br.com.brunolutterbach.ticketmasterapi.repository.IngressoRepository;
import br.com.brunolutterbach.ticketmasterapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@EnableScheduling
public class IngressoService {

    final IngressoRepository repository;
    final PayPalService payPalService;
    final IngressoValidator ingressoValidator;
    final UsuarioRepository usuarioRepository;
    final EmailService emailService;


    public void cadastrar(Ingresso ingresso) {
        repository.save(ingresso);
    }

    public DadosPedidoIngressoDetalhado comprarIngresso(DadosCompraIngresso dados, HttpServletRequest httpServletRequest) throws Exception {
        List<Ingresso> ingressos = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        var usuario = usuarioRepository.findById(1L).get();

        for (int i = 0; i < dados.quantidade(); i++) {
            var ingresso = repository.findFirstByEventoIdAndCompradorIsNull(dados.idEvento());

//            ingressoValidator.validarIngresso(usuario, ingresso, dados);

            ingresso.setComprador(usuario);
            ingresso.setStatusIngresso(StatusIngresso.AGUARDANDO_PAGAMENTO);
            ingresso.setDataReserva(LocalDateTime.now());
            ingressos.add(ingresso);

            valorTotal = valorTotal.add(ingresso.getValor());
            repository.save(ingresso);
        }

        var returnUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/ingresso/pagamento-concluido";
        var cancelUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/ingresso/pagamento-falhou";

        String linkPagamento = payPalService.createOrder(valorTotal, returnUrl, cancelUrl, ingressos);

        emailService.enviarEmailFinalizePgto(usuario.getEmail(), usuario.getNome(), ingressos.get(0).getEvento().getNome(),
                ingressos.get(0).getEvento().getDataEvento().atStartOfDay(), ingressos.get(0).getEvento().getHoraEvento(), valorTotal, linkPagamento);

        return new DadosPedidoIngressoDetalhado(ingressos.get(0), linkPagamento, dados);
    }

    public Ingresso buscarIngressoPorID(String uuid) {
        return repository.findById(UUID.fromString(uuid)).orElseThrow();
    }

    public List<DadosIngressosUsuario> listarIngressosDoUsuario(Long idUsuario) {
        return repository.findIngressosByCompradorId(idUsuario);
    }

    public List<Ingresso> buscarIngressoPorPaymentID(String paymentId) {
        return repository.findIngressoByPaymentId(paymentId);
    }

    @Scheduled(fixedDelay = 600000) // 5m
    public void liberarIngressosAguardandoPagamento() {
        List<Ingresso> ingressos = repository.findByCompradorIsNotNullAndStatusIngresso(StatusIngresso.AGUARDANDO_PAGAMENTO);

        var now = LocalDateTime.now();

        for (Ingresso ingresso : ingressos) {
            var dataReserva = ingresso.getDataReserva();
            if (dataReserva != null) {
                long seconds = ChronoUnit.SECONDS.between(dataReserva, now);
                if (seconds >= 10) {
                    ingresso.setComprador(null);
                    ingresso.setDataReserva(null);
                    ingresso.setStatusIngresso(StatusIngresso.CRIADO);
                    repository.save(ingresso);
                }
            }
        }
    }
}
