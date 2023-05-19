package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosCadastroUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import br.com.brunolutterbach.ticketmasterapi.repository.RoleRepository;
import br.com.brunolutterbach.ticketmasterapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UsuarioService {

    final UsuarioRepository repository;

    public DadosListagemUsuario cadastrar(DadosCadastroUsuario dados, RoleRepository roleRepository) {
        var usuario = new Usuario(dados);
        var roles = roleRepository.findByName("ROLE_USER").get();
        usuario.setRoles(Collections.singletonList(roles));
        repository.save(usuario);
        return new DadosListagemUsuario(usuario);
    }

    public DadosListagemUsuario buscarPorId(Long id) {
        var usuario = repository.findById(id).orElseThrow();
        return new DadosListagemUsuario(usuario);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
