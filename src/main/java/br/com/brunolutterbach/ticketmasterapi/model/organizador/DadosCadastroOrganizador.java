package br.com.brunolutterbach.ticketmasterapi.model.organizador;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroOrganizador(
        @NotBlank
        String nomeEmpresa,
        @NotBlank
        String email,
        @NotBlank
        String site,
        @NotBlank
        String cnpj,
        // @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (99) 9999-9999")
        String telefone
) {
}
