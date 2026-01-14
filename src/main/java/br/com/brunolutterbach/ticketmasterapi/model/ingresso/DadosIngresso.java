package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record DadosIngresso(
        @NotBlank
        Long eventoId,
        @DecimalMin(value = "0")
        BigDecimal valor,
        @Min(value = 1)
        int quantidadeDisponivel

) {
    public DadosIngresso(Ingresso ingresso) {
        this(ingresso.getEvento().getId(), ingresso.getValor(), ingresso.getEvento().getQuantidadeIngressos());
    }
}
