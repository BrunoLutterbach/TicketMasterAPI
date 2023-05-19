package br.com.brunolutterbach.ticketmasterapi.model.evento;

import java.util.List;

public record DadosAtualizacaoEvento(
        String nome,
        String descricao,
        List<String> imagens,
        String dataEvento,
        String statusEvento,
        String categoriaEvento

) {
    public DadosAtualizacaoEvento(Evento evento) {
        this(
                evento.getNome(),
                evento.getDescricao(),
                evento.getImagens(),
                evento.getDataEvento().toString(),
                evento.getStatusEvento().toString(),
                evento.getCategoriaEvento().toString()
        );
    }
}
