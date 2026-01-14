package br.com.brunolutterbach.ticketmasterapi.model.endereco;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEndereco(

        @NotBlank
        String logradouro,
        @NotBlank
        String cep,
        @NotBlank
        String numero,
        @NotBlank
        String cidade

) {
}
