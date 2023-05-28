package br.com.brunolutterbach.ticketmasterapi.controller;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosAtualizacaoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.service.EnderecoService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/endereco")
@AllArgsConstructor
public class EnderecoController {

    final EnderecoService enderecoService;
    final UsuarioLogadoUtil logadoUtil;

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    @Transactional
    public ResponseEntity<DadosEndereco> cadastrarEnderecoUsuario(@RequestBody @Valid DadosCadastroEndereco dados) {
        var endereco = enderecoService.cadastrar(dados, logadoUtil.obterUsuarioLogado());
        return ResponseEntity.ok(endereco);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosEndereco> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoEndereco dados) {
        var endereco = enderecoService.atualizar(id, dados);
        return ResponseEntity.ok(endereco);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DadosEndereco> buscarPorId(@PathVariable Long id) {
        var endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
