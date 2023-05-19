package br.com.brunolutterbach.ticketmasterapi.model.evento;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DadosCadastroEvento(

        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @Size(min = 1)
        List<String> imagens,
        @Min(value = 25)
        int quantidadeIngressoDisponivel,
        @DecimalMin(value = "15.00")
        BigDecimal valorIngresso,
        @NotNull
        Long organizadorId,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataEvento,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime horaEvento,
        @NotNull
        StatusEvento statusEvento,
        @NotNull
        CategoriaEvento categoriaEvento,
        @Valid
        DadosEndereco endereco
) {
}
