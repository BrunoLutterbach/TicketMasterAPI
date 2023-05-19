package br.com.brunolutterbach.ticketmasterapi.model.usuario;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        @Valid
        List<DadosEndereco> enderecos
) {
}
