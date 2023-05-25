package br.com.brunolutterbach.ticketmasterapi.model.organizador;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuario;

public record DadosListagemUsuarioOrganizador(
        DadosListagemUsuario usuario,
        String nomeEmpresa,
        String email,
        String site,
        String cnpj,
        String telefone
) {
    public DadosListagemUsuarioOrganizador(Organizador organizador) {
        this(
                new DadosListagemUsuario(organizador.getUsuario()),
                organizador.getNomeEmpresa(),
                organizador.getEmail(),
                organizador.getSite(),
                organizador.getCnpj(),
                organizador.getTelefone()
        );
    }
}
