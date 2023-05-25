package br.com.brunolutterbach.ticketmasterapi.utils;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import br.com.brunolutterbach.ticketmasterapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioLogadoUtil {

    final UsuarioRepository usuarioRepository;

    public Usuario obterUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usuarioRepository.findByEmail(username);
    }
}
