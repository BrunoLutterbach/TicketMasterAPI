package br.com.brunolutterbach.ticketmasterapi.model.ingresso.validacoes;

import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosCompraIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;

@Component
public class IngressoValidator {

    public void validarIngresso(Usuario comprador, Ingresso ingresso, DadosCompraIngresso dados) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso não encontrado para o evento");
        }
        if (ingresso.isUtilizado()) {
            throw new IllegalArgumentException("Ingresso já foi utilizado");
        }
        if (ingresso.getComprador() != null) {
            throw new IllegalArgumentException("Ingresso já foi comprado por outro usuário");
        }
        if (comprador == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (ingresso.getEvento().getDataEvento().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new IllegalArgumentException("Data do evento já passou");
        }
        if (ingresso.getEvento().getDataEvento().isEqual(ChronoLocalDate.from(LocalDateTime.now())) && ingresso.getEvento().getHoraEvento().isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("Horário do evento já passou");
        }
        if (ingresso.getEvento().getQuantidadeIngressos() < dados.quantidade()) {
            throw new IllegalArgumentException("Quantidade de ingressos excede a capacidade do local");
        }
        if (dados.quantidade() < 1) {
            throw new IllegalArgumentException("Quantidade de ingressos deve ser maior que 0");
        }
    }
}
