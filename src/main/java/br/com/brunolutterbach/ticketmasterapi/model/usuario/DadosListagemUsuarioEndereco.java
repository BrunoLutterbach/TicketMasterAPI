package br.com.brunolutterbach.ticketmasterapi.model.usuario;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;

import java.util.List;

public record DadosListagemUsuarioEndereco(
        Long id, String nome, String email, List<DadosEndereco> endereco
) {
    public DadosListagemUsuarioEndereco(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getEnderecos().stream().map(DadosEndereco::new).toList());
    }
}
