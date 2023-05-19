package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record DadosIngresso(
        @NotBlank
        Long eventoId,
        @DecimalMin(value = "0")
        BigDecimal valor,
        @Min(value = 1)
        int quantidadeDisponivel

) {
}
