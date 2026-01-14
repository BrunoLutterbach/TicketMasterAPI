package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.exception.ValidacaoException;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosAtualizacaoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.model.usuario.Usuario;
import br.com.brunolutterbach.ticketmasterapi.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    final EnderecoRepository repository;


    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Endereço não encontrado");
        }
        repository.deleteById(id);
    }


    public DadosEndereco cadastrar(DadosCadastroEndereco dados, Usuario usuario) {
        var endereco = new Endereco(dados);
        usuario.getEnderecos().add(endereco);
        repository.save(endereco);
        return new DadosEndereco(endereco);
    }

    public DadosEndereco atualizar(Long id, DadosAtualizacaoEndereco dados) {
        var endereco = repository.getReferenceById(id);
        endereco.atualizar(dados);
        repository.save(endereco);
        return new DadosEndereco(endereco);
    }

    public DadosEndereco buscarPorId(Long id) {
        var endereco = repository.getReferenceById(id);
        return new DadosEndereco(endereco);
    }
}
