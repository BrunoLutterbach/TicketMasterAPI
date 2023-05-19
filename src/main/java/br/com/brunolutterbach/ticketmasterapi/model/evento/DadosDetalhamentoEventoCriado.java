package br.com.brunolutterbach.ticketmasterapi.model.evento;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosDetalhamentoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;

import java.math.BigDecimal;
import java.util.List;

public record DadosDetalhamentoEventoCriado(
        String nome,
        String descricao,
        List<String> imagens,
        int quantidadeIngressoDisponivel,
        BigDecimal valorIngresso,
        String dataEvento,
        StatusEvento statusEvento,
        CategoriaEvento categoriaEvento,
        DadosDetalhamentoEndereco endereco
) {
    public DadosDetalhamentoEventoCriado(Evento evento, BigDecimal valorIngresso) {
        this(evento.getNome(), evento.getDescricao(), evento.getImagens(), evento.getIngresso().size(), valorIngresso, evento.getDataEvento().toString(), evento.getStatusEvento(), evento.getCategoriaEvento(), new DadosDetalhamentoEndereco(evento.getEnderecoEvento()));
    }
}
