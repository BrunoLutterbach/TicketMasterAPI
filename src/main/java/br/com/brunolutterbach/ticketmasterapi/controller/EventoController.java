package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.evento.*;
import br.com.brunolutterbach.ticketmasterapi.service.EventoService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/evento")
@AllArgsConstructor
public class EventoController {

    final EventoService eventoService;
    final UsuarioLogadoUtil logadoUtil;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping()
    @Transactional
    public ResponseEntity<DadosDetalhamentoEventoCriado> cadastrarEvento(@RequestBody @Valid DadosCadastroEvento dados, UriComponentsBuilder uri) {
        var usuarioLogado = logadoUtil.obterUsuarioLogado();
        var evento = new Evento(dados, usuarioLogado);
        eventoService.cadastrar(evento, dados);
        var uriEvento = uri.path("/api/eventos/{id}").buildAndExpand(evento.getId()).toUri();
        var valorIngresso = dados.valorIngresso();
        return ResponseEntity.created(uriEvento).body(new DadosDetalhamentoEventoCriado(evento, valorIngresso));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoEvento> atualizarEvento(@PathVariable Long id, @RequestBody DadosAtualizacaoEvento dados) throws Exception {
        var evento = eventoService.atualizarInformacoes(id, dados);
        return ResponseEntity.ok(evento);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR', 'USER')")
    @GetMapping()
    public ResponseEntity<Page<DadosDetalhamentoEvento>> listarEventos(Pageable pageable) {
        var eventos = eventoService.listarEventos(pageable);
        return ResponseEntity.ok(eventos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoEvento> obterEventoPorId(@PathVariable Long id) {
        var evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarEvento(@PathVariable Long id) {
        eventoService.deletarEvento(id);
        return ResponseEntity.noContent().build();
    }
}
