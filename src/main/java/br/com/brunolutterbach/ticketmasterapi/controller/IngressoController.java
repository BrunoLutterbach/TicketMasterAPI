package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.*;
import br.com.brunolutterbach.ticketmasterapi.service.EmailService;
import br.com.brunolutterbach.ticketmasterapi.service.EventoService;
import br.com.brunolutterbach.ticketmasterapi.service.IngressoService;
import br.com.brunolutterbach.ticketmasterapi.service.PayPalService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ingresso")
@AllArgsConstructor
public class IngressoController {

    final IngressoService ingressoService;
    final EventoService eventoService;
    final PayPalService payPalService;
    final EmailService emailService;
    final UsuarioLogadoUtil logadoUtil;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comprar")
    public ResponseEntity<DadosPedidoIngressoDetalhado> comprarIngresso(@RequestBody DadosCompraIngresso dados, HttpServletRequest httpServletRequest) throws Exception {
        var pedido = ingressoService.comprarIngresso(dados, httpServletRequest);
        return ResponseEntity.ok(pedido);
    }

    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosIngresso> atualizarValorIngressos(@RequestBody DadosAtualizacaoIngresso dados, @PathVariable Long id) {
        var ingresso = ingressoService.atualizarValorIngresso(dados, id);
        return ResponseEntity.ok(ingresso);
    }

    @GetMapping("/pagamento-concluido")
    public String pagamentoConcluido(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        var payment = payPalService.executePayment(paymentId, payerId);

        var ingressos = ingressoService.buscarIngressoPorPaymentID(payment.getId());
        for (var ingresso : ingressos) {
            ingresso.setStatusIngresso(StatusIngresso.PAGO);
            ingresso.setDataCompra(LocalDateTime.now());
            ingressoService.cadastrar(ingresso);
        }

        var valorTotalDouble = ingressos.stream().mapToDouble(ingresso -> ingresso.getValor().doubleValue()).sum();
        var valorTotal = BigDecimal.valueOf(valorTotalDouble);

        String endereco = ingressos.get(0).getEvento().getEnderecoEvento().getLogradouro() + ", "
                + ingressos.get(0).getEvento().getEnderecoEvento().getNumero() + " - "
                + ingressos.get(0).getEvento().getEnderecoEvento().getCidade();

        List<DadosIngressoQrCode> dadosIngressoQrCodes = new ArrayList<>();
        for (var ingresso : ingressos) {
            dadosIngressoQrCodes.add(new DadosIngressoQrCode(ingresso.getId(), ingressos.get(0).getComprador().getNome()));
        }

        emailService.enviarEmailConfirmacao(ingressos.get(0).getComprador().getEmail(), ingressos.get(0).getComprador().getNome(),
                ingressos.get(0).getEvento().getNome(), ingressos.get(0).getEvento().getDataEvento(),
                ingressos.get(0).getEvento().getHoraEvento(), valorTotal, endereco, dadosIngressoQrCodes);

        return "Pagamento concluído com sucesso!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<List<DadosIngressosUsuario>> listarIngressosDoUsuarioPorId(@PathVariable Long id) {
        var ingressos = ingressoService.listarIngressosDoUsuario(id);
        return ResponseEntity.ok(ingressos);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<DadosIngressosUsuario>> listarIngressosDoUsuario() {
        var ingressos = ingressoService.listarIngressosDoUsuario(logadoUtil.obterUsuarioLogado().getId());
        return ResponseEntity.ok(ingressos);
    }

}

