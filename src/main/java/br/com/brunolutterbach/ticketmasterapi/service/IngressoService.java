package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.exception.ValidacaoException;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.*;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.validacoes.IngressoValidator;
import br.com.brunolutterbach.ticketmasterapi.repository.EventoRepository;
import br.com.brunolutterbach.ticketmasterapi.repository.IngressoRepository;
import br.com.brunolutterbach.ticketmasterapi.repository.UsuarioRepository;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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
    final UsuarioLogadoUtil logadoUtil;
    final EventoRepository eventoRepository;


    public void cadastrar(Ingresso ingresso) {
        repository.save(ingresso);
    }

    public DadosPedidoIngressoDetalhado comprarIngresso(DadosCompraIngresso dados, HttpServletRequest httpServletRequest) throws Exception {
        List<Ingresso> ingressos = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        var usuario = logadoUtil.obterUsuarioLogado();

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

    @Scheduled(fixedDelay = 300000)
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
                    ingresso.setPaymentId(null);
                    repository.save(ingresso);
                }
            }
        }
    }

    public DadosIngresso atualizarValorIngresso(DadosAtualizacaoIngresso dados, Long id) {
        var evento = eventoRepository.findById(id).orElseThrow();
        if (evento.getDataEvento().isBefore(ChronoLocalDate.from(LocalDateTime.now())) ||
                evento.getHoraEvento().isBefore(LocalDateTime.now().toLocalTime())) {
            throw new ValidacaoException("O evento já começou ou passou, não é possível alterar o valor do ingresso.");
        }

        var ingressos = repository.findByEventoIdAndCompradorIsNull(id);
        for (var ingresso : ingressos) {
            ingresso.setValor(dados.valor());
            repository.save(ingresso);
        }
        return new DadosIngresso(ingressos.get(0));
    }
}
