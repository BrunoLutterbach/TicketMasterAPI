package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoIngresso(
        BigDecimal valor,
        Integer quantidadeDisponivel,
        LocalDateTime dataCompra,
        LocalDateTime dataValidade
) {
    public DadosDetalhamentoIngresso(Ingresso ingressos) {
        this(ingressos.getValor(), ingressos.getEvento().getQuantidadeIngressos(), ingressos.getDataCompra(), ingressos.getDataValidade());
    }
}
