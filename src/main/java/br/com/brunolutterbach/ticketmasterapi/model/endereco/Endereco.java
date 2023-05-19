package br.com.brunolutterbach.ticketmasterapi.model.endereco;

import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import lombok.Data;

import javax.persistence.*;

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

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.cidade = endereco.cidade();
    }

    public Endereco() {
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
}
