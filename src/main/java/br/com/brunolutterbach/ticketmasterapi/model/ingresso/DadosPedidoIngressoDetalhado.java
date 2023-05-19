package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import java.math.BigDecimal;

public record DadosPedidoIngressoDetalhado(
        String evento,
        BigDecimal valorUnitario,
        int quantidade,
        BigDecimal valorTotal,
        String linkPagamento

) {

    public DadosPedidoIngressoDetalhado(Ingresso ingresso, String linkPagamento, DadosCompraIngresso dados) {
        this(
                ingresso.getEvento().getNome(),
                ingresso.getValor(),
                dados.quantidade(),
                ingresso.getValor().multiply(BigDecimal.valueOf(dados.quantidade())),
                linkPagamento
        );
    }

}
