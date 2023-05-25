package br.com.brunolutterbach.ticketmasterapi.controller;


import br.com.brunolutterbach.ticketmasterapi.model.organizador.DadosCadastroOrganizador;
import br.com.brunolutterbach.ticketmasterapi.model.organizador.DadosListagemUsuarioOrganizador;
import br.com.brunolutterbach.ticketmasterapi.service.OrganizadorService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/organizador")
@AllArgsConstructor
public class OrganizadorController {

    final OrganizadorService orgService;
    final UsuarioLogadoUtil logadoUtil;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @Transactional
    public ResponseEntity<DadosListagemUsuarioOrganizador> cadastrarOrganizador(@RequestBody DadosCadastroOrganizador dados) {
        var usuarioLogado = logadoUtil.obterUsuarioLogado();
        var organizador = orgService.cadastrarOrganizador(dados, usuarioLogado);
        return ResponseEntity.ok(organizador);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemUsuarioOrganizador> atualizarOrganizador(@RequestBody DadosCadastroOrganizador dados, @PathVariable Long id) {
        var organizador = orgService.atualizarOrganizador(dados, id);
        return ResponseEntity.ok(organizador);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR', 'USER')")
    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuarioOrganizador>> listarOrganizadores(Pageable pageable) {
        var organizadores = orgService.listarOrganizadores(pageable);
        return ResponseEntity.ok(organizadores);
    }


}
