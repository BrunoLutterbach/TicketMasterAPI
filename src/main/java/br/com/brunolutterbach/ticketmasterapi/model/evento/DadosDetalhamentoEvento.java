package br.com.brunolutterbach.ticketmasterapi.model.evento;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosDetalhamentoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;

import java.math.BigDecimal;
import java.util.List;

public record DadosDetalhamentoEvento(

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
    public DadosDetalhamentoEvento(Evento evento) {
        this(
                evento.getNome(),
                evento.getDescricao(),
                evento.getImagens(),
                evento.getQuantidadeIngressos(),
                evento.getIngresso().get(0).getValor(),
                String.valueOf(evento.getDataEvento()),
                evento.getStatusEvento(),
                evento.getCategoriaEvento(),
                new DadosDetalhamentoEndereco(evento.getEnderecoEvento()));
    }
}
