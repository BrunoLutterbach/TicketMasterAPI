package br.com.brunolutterbach.ticketmasterapi.model.evento;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> imagens;
    private int quantidadeIngressos;
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingresso> ingresso = new ArrayList<>();
    private LocalDate dataEvento;
    private LocalTime horaEvento;
    private LocalDate dataAnuncio = LocalDate.now();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco enderecoEvento;
    @Enumerated(EnumType.STRING)
    private StatusEvento statusEvento;
    @Enumerated(EnumType.STRING)
    private CategoriaEvento categoriaEvento;


    public Evento(DadosCadastroEvento dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.imagens = dados.imagens();
        this.quantidadeIngressos = dados.quantidadeIngressoDisponivel();
        this.dataEvento = LocalDate.from(dados.dataEvento());
        this.horaEvento = LocalTime.from(dados.horaEvento());
        this.statusEvento = dados.statusEvento();
        this.categoriaEvento = dados.categoriaEvento();
        this.enderecoEvento = new Endereco(dados.endereco());
    }

    public Evento() {

    }

    public void atualizar(DadosAtualizacaoEvento dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.imagens() != null) {
            this.imagens = dados.imagens();
        }
        if (dados.dataEvento() != null) {
            this.dataEvento = LocalDate.from(LocalDateTime.parse(dados.dataEvento()));
        }
        if (dados.dataEvento() != null) {
            this.horaEvento = LocalTime.from(LocalDateTime.parse(dados.dataEvento()));
        }
        if (dados.statusEvento() != null) {
            this.statusEvento = StatusEvento.valueOf(dados.statusEvento());
        }
        if (dados.categoriaEvento() != null) {
            this.categoriaEvento = CategoriaEvento.valueOf(dados.categoriaEvento());
        }
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", imagens=" + imagens +
                ", quantidadeIngressos=" + quantidadeIngressos +
                ", dataEvento=" + dataEvento +
                ", horaEvento=" + horaEvento +
                ", dataAnuncio=" + dataAnuncio +
                ", enderecoEvento=" + enderecoEvento +
                ", statusEvento=" + statusEvento +
                ", categoriaEvento=" + categoriaEvento +
                '}';
    }

}
