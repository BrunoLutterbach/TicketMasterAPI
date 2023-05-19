package br.com.brunolutterbach.ticketmasterapi.repository;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
