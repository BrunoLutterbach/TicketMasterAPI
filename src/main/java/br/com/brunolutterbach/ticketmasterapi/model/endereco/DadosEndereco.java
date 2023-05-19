package br.com.brunolutterbach.ticketmasterapi.model.endereco;

import javax.validation.constraints.NotBlank;

public record DadosEndereco(
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