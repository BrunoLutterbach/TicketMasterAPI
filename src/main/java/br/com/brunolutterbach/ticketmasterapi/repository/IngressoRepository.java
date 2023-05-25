package br.com.brunolutterbach.ticketmasterapi.repository;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressosUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, UUID> {
    Ingresso findFirstByEventoIdAndCompradorIsNull(Long aLong);

    Optional<Ingresso> findById(UUID id);

    @Query("SELECT new br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressosUsuario(i.evento.nome, i.evento.descricao, i.evento.dataEvento, i.evento.horaEvento, i.evento.statusEvento, i.valor, i.dataCompra, i.dataValidade) FROM Ingresso i WHERE i.comprador.id = ?1")
    List<DadosIngressosUsuario> findIngressosByCompradorId(Long id);

    List<Ingresso> findIngressoByPaymentId(String paymentId);

    List<Ingresso> findByCompradorIsNotNullAndStatusIngresso(StatusIngresso statusIngresso);

}
