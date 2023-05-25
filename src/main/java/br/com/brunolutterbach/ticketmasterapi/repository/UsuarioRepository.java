package br.com.brunolutterbach.ticketmasterapi.repository;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Object> findOptionalByEmail(String email);

    Usuario findByEmail(String email);
}
