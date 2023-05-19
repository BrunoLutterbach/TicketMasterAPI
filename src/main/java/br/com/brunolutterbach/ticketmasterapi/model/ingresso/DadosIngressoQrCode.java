package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import java.util.UUID;

public record DadosIngressoQrCode(
        UUID uuid,
        String nomeComprador
) {
}
