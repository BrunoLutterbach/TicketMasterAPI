package br.com.brunolutterbach.ticketmasterapi.controller;


import br.com.brunolutterbach.ticketmasterapi.model.usuario.DadosAutenticacaoUsuario;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import br.com.brunolutterbach.ticketmasterapi.security.DadosTokenJWT;
import br.com.brunolutterbach.ticketmasterapi.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AutenticacaoController {

    final AuthenticationManager authManager;
    final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacaoUsuario dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authenticate = authManager.authenticate(authenticationToken);
        var token = tokenService.gerarToken((Usuario) authenticate.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(token));
    }
}
