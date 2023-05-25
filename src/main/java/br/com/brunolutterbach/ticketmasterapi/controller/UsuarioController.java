package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosCadastroUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuario;
import br.com.brunolutterbach.ticketmasterapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    final UsuarioService usuarioService;

    @PostMapping()
    @Transactional
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody DadosCadastroUsuario dados, UriComponentsBuilder builder) {
        var usuario = usuarioService.cadastrar(dados);
        var uri = builder.path("/api/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> getUsuario(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
