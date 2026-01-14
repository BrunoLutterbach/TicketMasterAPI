package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosAtualizacaoUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosCadastroUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosListagemUsuarioEndereco;
import br.com.brunolutterbach.ticketmasterapi.service.UsuarioService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsuarioController {

    final UsuarioService usuarioService;
    final UsuarioLogadoUtil logadoUtil;

    @PostMapping()
    @Transactional
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody DadosCadastroUsuario dados, UriComponentsBuilder builder) {
        var usuario = usuarioService.cadastrar(dados);
        var uri = builder.path("/api/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping()
    @Transactional
    public ResponseEntity<DadosListagemUsuario> atualizar(@RequestBody DadosAtualizacaoUsuario dados) {
        var usuario = usuarioService.atualizar(dados, logadoUtil.obterUsuarioLogado());
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/enderecos")
    public ResponseEntity<List<DadosEndereco>> listarEnderecos() {
        var usuario = usuarioService.buscarEnderecos(logadoUtil.obterUsuarioLogado().getId());
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> getUsuario(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/enderecos")
    public ResponseEntity<DadosListagemUsuarioEndereco> getUsuarioComEndereco(@PathVariable Long id) {
        var usuario = usuarioService.buscarUsuarioComEndereco(id);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
