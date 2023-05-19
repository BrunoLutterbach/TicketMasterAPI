package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosCadastroUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuario;
import br.com.brunolutterbach.ticketmasterapi.repository.RoleRepository;
import br.com.brunolutterbach.ticketmasterapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Collection;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    final UsuarioService usuarioService;
    final RoleRepository roleRepository;

    @PostMapping()
    @Transactional
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody DadosCadastroUsuario dados, UriComponentsBuilder builder) {
        var usuario = usuarioService.cadastrar(dados, roleRepository);
        var uri = builder.path("/api/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> getUsuario(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("AUTORIDADES: " + authorities);
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
