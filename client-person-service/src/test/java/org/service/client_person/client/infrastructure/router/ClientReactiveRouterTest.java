package org.service.client_person.client.infrastructure.router;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.service.client_person.client.domain.model.ClientDTO;
import org.service.client_person.client.domain.port.out.repository.IClientRepository;
import org.service.client_person.person.domain.model.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ClientReactiveRouterTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IClientRepository repository;

    @Test
    void testClientReactiveRouter() {
        ClientReactiveRouter router = new ClientReactiveRouter();
        assertNotNull(router);
    }

    @Test
    void testCreateClientOk() {
        ClientDTO requestClientDTO = buildRequestClientDTO();
        ClientDTO savedClientDTO = buildSavedClientDTO(requestClientDTO);

        Mockito.when(repository.create(requestClientDTO)).thenReturn(savedClientDTO);

        webTestClient.post()
                .uri("/clientes")
                .body(Mono.just(requestClientDTO), ClientDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientDTO.class)
                .consumeWith(result-> {
                    ClientDTO responseClientDTO = result.getResponseBody();
                    assertNotNull(responseClientDTO);
                    assertNull(requestClientDTO.id());
                    assertNotNull(responseClientDTO.id());
                    assertEquals(requestClientDTO.person().identification(), responseClientDTO.person().identification());
                });
    }

    @Test
    void testGetAllClientOk() {
        List<ClientDTO> clientListDTO = buildClientDTOList();
        Mockito.when(repository.findAll()).thenReturn(clientListDTO);

        webTestClient.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ClientDTO.class)
                .isEqualTo(clientListDTO)
                .hasSize(clientListDTO.size());
    }

    private static List<ClientDTO> buildClientDTOList() {
        return List.of(
                new ClientDTO(UUID.randomUUID(), "12345", true,
                        new PersonDTO(UUID.randomUUID(), "person1", "M", 18, "123456789", "NA", "1800100100")),
                new ClientDTO(UUID.randomUUID(), "12345", true,
                        new PersonDTO(UUID.randomUUID(), "person2", "M", 18, "023456789", "NA", "1800100100")));
    }

    private static ClientDTO buildRequestClientDTO() {
        return new ClientDTO(null, "12345", true,
                        new PersonDTO(null, "person1", "M", 18, "123456789", "NA", "1800100100"));
    }


    private static ClientDTO buildSavedClientDTO(ClientDTO requestClientDTO) {
        return new ClientDTO(UUID.randomUUID(), requestClientDTO.password(), requestClientDTO.state(),
                new PersonDTO(UUID.randomUUID(), requestClientDTO.person().name(), requestClientDTO.person().gender(),
                        requestClientDTO.person().age(), requestClientDTO.person().identification(),
                        requestClientDTO.person().address(),
                        requestClientDTO.person().phone()));
    }
}