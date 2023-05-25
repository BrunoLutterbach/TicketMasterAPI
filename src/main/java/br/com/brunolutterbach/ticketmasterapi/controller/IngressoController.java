package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosCompraIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressoQrCode;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressosUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosPedidoIngressoDetalhado;
import br.com.brunolutterbach.ticketmasterapi.service.EmailService;
import br.com.brunolutterbach.ticketmasterapi.service.EventoService;
import br.com.brunolutterbach.ticketmasterapi.service.IngressoService;
import br.com.brunolutterbach.ticketmasterapi.service.PayPalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/comprar")
    public ResponseEntity<DadosPedidoIngressoDetalhado> comprarIngresso(@RequestBody DadosCompraIngresso dados, HttpServletRequest httpServletRequest) throws Exception {
        var pedido = ingressoService.comprarIngresso(dados, httpServletRequest);
        return ResponseEntity.ok(pedido);
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
    public ResponseEntity<List<DadosIngressosUsuario>> listarIngressosDoUsuario(@PathVariable Long id) {
        var ingressos = ingressoService.listarIngressosDoUsuario(id);
        return ResponseEntity.ok(ingressos);
    }
}
