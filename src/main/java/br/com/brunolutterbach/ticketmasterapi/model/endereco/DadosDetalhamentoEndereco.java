package br.com.brunolutterbach.ticketmasterapi.model.endereco;

public record DadosDetalhamentoEndereco(Long id, String logradouro, String cep, String numero, String cidade) {

    public DadosDetalhamentoEndereco(Endereco endereco) {
        this(endereco.getId(), endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade());
    }
}
