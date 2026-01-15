package br.com.brunolutterbach.ticketmasterapi;

import br.com.brunolutterbach.ticketmasterapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.ticketmasterapi.model.endereco.Endereco;
import br.com.brunolutterbach.ticketmasterapi.repository.EnderecoRepository;
import br.com.brunolutterbach.ticketmasterapi.security.TestSecurityConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class EnderecoControllerIntTestV2 {

    // Teste TestContainers + RestAssured

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EnderecoRepository enderecoRepository;

    @LocalServerPort
    private int port;

    Long enderecoId;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        enderecoRepository.deleteAll();
        Endereco e = enderecoRepository.save(
                new Endereco(new DadosCadastroEndereco("Rua Teste", "28640000", "21", "Carmo"))
        );
        enderecoId = e.getId();
    }

    @Test
    void shouldFindById() {
        RestAssured
                .given()
                .when()
                .get("/api/endereco/" + enderecoId)
                .then()
                .statusCode(200)
                .body("logradouro", equalTo("Rua Teste"));
    }
}