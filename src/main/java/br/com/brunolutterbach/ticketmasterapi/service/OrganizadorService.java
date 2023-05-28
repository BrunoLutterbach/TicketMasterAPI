package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.organizador.DadosCadastroOrganizador;
import br.com.brunolutterbach.ticketmasterapi.model.organizador.DadosListagemUsuarioOrganizador;
import br.com.brunolutterbach.ticketmasterapi.model.organizador.Organizador;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import br.com.brunolutterbach.ticketmasterapi.repository.OrganizadorRepository;
import br.com.brunolutterbach.ticketmasterapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizadorService {

    final OrganizadorRepository repository;
    final RoleRepository roleRepository;


    public DadosListagemUsuarioOrganizador cadastrarOrganizador(DadosCadastroOrganizador dados, Usuario usuarioLogado) {
        if (usuarioLogado.getOrganizador() != null) {
            throw new IllegalStateException("O usuário já possui um organizador.");
        }

        var organizador = new Organizador(dados, usuarioLogado);
        usuarioLogado.getRoles().add(roleRepository.findByName("ROLE_ORGANIZADOR").get());
        repository.save(organizador);
        return new DadosListagemUsuarioOrganizador(organizador);
    }

    public DadosListagemUsuarioOrganizador atualizarOrganizador(DadosCadastroOrganizador dados, Long id) {
        var organizador = repository.getReferenceById(id);
        organizador.atualizar(dados);
        repository.save(organizador);
        return new DadosListagemUsuarioOrganizador(organizador);
    }

    public Page<DadosListagemUsuarioOrganizador> listarOrganizadores(Pageable pageable) {
        return repository.findAll(pageable).map(DadosListagemUsuarioOrganizador::new);
    }
}
