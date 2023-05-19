package br.com.brunolutterbach.ticketmasterapi.model.usuario;

public record DadosListagemUsuario(Long id, String nome, String email) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
