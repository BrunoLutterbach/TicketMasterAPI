package br.com.brunolutterbach.ticketmasterapi.model.endereco;

import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    @OneToOne(mappedBy = "enderecoEvento")
    private Evento evento;

    public Endereco() {
    }

    public Endereco(DadosCadastroEndereco dados) {
        this.logradouro = dados.logradouro();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.cidade = dados.cidade();
    }

    public Endereco(DadosCadastroEndereco dados, Usuario usuario) {
        this.logradouro = dados.logradouro();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.cidade = dados.cidade();
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", cep='" + cep + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }

    public void atualizar(DadosAtualizacaoEndereco dados) {
        if (dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        }
        if (dados.cep() != null) {
            this.cep = dados.cep();
        }
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.cidade() != null) {
            this.cidade = dados.cidade();
        }
    }
}
