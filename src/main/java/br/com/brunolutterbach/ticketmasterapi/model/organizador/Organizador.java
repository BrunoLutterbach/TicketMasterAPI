package br.com.brunolutterbach.ticketmasterapi.model.organizador;

import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeEmpresa;
    private String email;
    private String site;
    private String cnpj;
    private String telefone;
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;

    public Organizador(DadosCadastroOrganizador dados, Usuario usuarioLogado) {
        this.nomeEmpresa = dados.nomeEmpresa();
        this.email = dados.email();
        this.site = dados.site();
        this.cnpj = dados.cnpj();
        this.telefone = dados.telefone();
        this.usuario = usuarioLogado;
    }

    public Organizador() {

    }

    public void atualizar(DadosAtualizacaoOrganizador dados) {
        if (dados.nomeEmpresa() != null) {
            this.nomeEmpresa = dados.nomeEmpresa();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.site() != null) {
            this.site = dados.site();
        }
        if (dados.cnpj() != null) {
            this.cnpj = dados.cnpj();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
    }
}
