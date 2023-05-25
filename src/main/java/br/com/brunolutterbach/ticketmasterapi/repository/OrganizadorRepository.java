package br.com.brunolutterbach.ticketmasterapi.repository;

import br.com.brunolutterbach.ticketmasterapi.model.organizador.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {
}
