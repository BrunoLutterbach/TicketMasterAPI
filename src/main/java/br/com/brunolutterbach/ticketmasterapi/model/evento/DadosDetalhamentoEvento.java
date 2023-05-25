package br.com.brunolutterbach.ticketmasterapi.model.evento;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosDetalhamentoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;
import br.com.brunolutterbach.ticketmasterapi.model.organizador.DadosListagemOrganizador;

import java.math.BigDecimal;
import java.util.List;

public record DadosDetalhamentoEvento(

        DadosListagemOrganizador organizador,
        String nomeEvento,
        String descricaoEvento,
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
                new DadosListagemOrganizador(
                        evento.getOrganizador().getNomeEmpresa(),
                        evento.getOrganizador().getEmail(),
                        evento.getOrganizador().getSite()),
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
