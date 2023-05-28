package br.com.brunolutterbach.ticketmasterapi.model.endereco;

public record DadosEndereco(
        String logradouro,
        String cep,
        String numero,
        String cidade
) {
    public DadosEndereco(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade());
    }

}