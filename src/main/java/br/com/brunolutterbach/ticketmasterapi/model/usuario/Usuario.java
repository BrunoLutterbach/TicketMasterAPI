package br.com.brunolutterbach.ticketmasterapi.model.usuario;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import br.com.brunolutterbach.ticketmasterapi.model.organizador.Organizador;
import br.com.brunolutterbach.ticketmasterapi.security.Roles;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Endereco> enderecos;
    @OneToMany(mappedBy = "comprador")
    private List<Ingresso> ingressos;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Roles> roles = new ArrayList<>();
    @OneToOne(mappedBy = "usuario")
    @JoinColumn(unique = true)
    private Organizador organizador;


    public Usuario(DadosCadastroUsuario dados) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = encoder.encode(dados.senha());
        if (dados.enderecos() != null) {
            this.enderecos = new ArrayList<>();
            dados.enderecos().forEach(endereco -> this.enderecos.add(new Endereco(endereco)));
        } else {
            this.enderecos = new ArrayList<>();
        }
    }

    public Usuario() {

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", enderecos=" + enderecos +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
