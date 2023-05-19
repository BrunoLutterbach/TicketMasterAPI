package br.com.brunolutterbach.ticketmasterapi.model.ingresso;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Ingresso {

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    private BigDecimal valor = BigDecimal.ZERO;
    private LocalDateTime dataCompra;
    private LocalDateTime dataValidade;
    private LocalDateTime dataReserva;
    private boolean isUtilizado = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;
    @ManyToOne
    private Usuario comprador = null;
    private String paymentId;
    @Enumerated(EnumType.STRING)
    private StatusIngresso statusIngresso = StatusIngresso.CRIADO;

    public Ingresso() {

    }
}
