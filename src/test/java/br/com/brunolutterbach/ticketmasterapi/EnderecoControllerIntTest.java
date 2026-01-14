package br.com.brunolutterbach.ticketmasterapi;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosAtualizacaoEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.repository.EnderecoRepository;
import br.com.brunolutterbach.ticketmasterapi.security.TestSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class EnderecoControllerIntTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EnderecoRepository enderecoRepository;

    Long enderecoId;

    @BeforeEach
    void setUp() {
        enderecoRepository.deleteAll();

        Endereco e = enderecoRepository.save(
                new Endereco(new DadosCadastroEndereco("Rua Teste", "28640000", "21", "Carmo"))
        );

        enderecoId = e.getId();
    }


    @Test
    void testFindById() {
        ResponseEntity<DadosEndereco> response = restTemplate.getForEntity("/api/endereco/" + enderecoId, DadosEndereco.class);
        log.info("Response: {}", response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Rua Teste", response.getBody().logradouro());
    }

    @Test
    void updateEndereco() {
        DadosAtualizacaoEndereco dados = new DadosAtualizacaoEndereco("Rua Teste Atualizada", "28640000", "21", "Carmo");
        ResponseEntity<DadosEndereco> response = restTemplate.exchange("/api/endereco/" + enderecoId, HttpMethod.PUT, new HttpEntity<>(dados), DadosEndereco.class);
        log.info("Response: {}", response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Rua Teste Atualizada", response.getBody().logradouro());
        Assertions.assertNotEquals("Rua Teste", response.getBody().logradouro());
    }
}
