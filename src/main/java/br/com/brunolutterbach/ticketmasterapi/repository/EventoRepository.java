package br.com.brunolutterbach.ticketmasterapi.repository;

import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
