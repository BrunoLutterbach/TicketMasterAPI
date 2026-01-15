package br.com.brunolutterbach.ticketmasterapi;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.repository.UsuarioRepository;
import br.com.brunolutterbach.ticketmasterapi.security.TestSecurityConfig;
import br.com.brunolutterbach.ticketmasterapi.security.TokenService;
import br.com.brunolutterbach.ticketmasterapi.service.EnderecoService;
import br.com.brunolutterbach.ticketmasterapi.utils.UsuarioLogadoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import br.com.brunolutterbach.ticketmasterapi.controller.EnderecoController;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class EnderecoControllerTest {

    // Teste MockMvc

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @MockBean
    UsuarioLogadoUtil usuarioLogadoUtil;

    @MockBean
    TokenService tokenService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @Test
    void shouldReturnAllEnderecos() throws Exception {
        // Arrange
        List<DadosEndereco> retorno = List.of(
                new DadosEndereco(new Endereco(new DadosCadastroEndereco("Rua Teste 1", "28640000", "21", "Carmo"))),
                new DadosEndereco(new Endereco(new DadosCadastroEndereco("Rua Teste 2", "28640000", "21", "Carmo")))
        );

        when(enderecoService.listarTodosEnderecos()).thenReturn(retorno);

        // Act & Assert
        mockMvc.perform(get("/api/endereco/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].logradouro").value("Rua Teste 1"))
                .andExpect(jsonPath("$[1].logradouro").value("Rua Teste 2"))
                .andExpect(jsonPath("$.length()").value(2));
    }

}
