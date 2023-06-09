package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusEvento;
import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DadosIngressosUsuario(
        StatusIngresso statusIngresso,
        String nome,
        String descricao,
        LocalDate dataEvento,
        LocalTime horaEvento,
        StatusEvento statusEvento,
        BigDecimal valor,
        LocalDateTime dataCompra,
        LocalDateTime dataValidade
) {

}
