package org.service.client_person.person.infrastructure.router;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.service.client_person.person.domain.model.PersonDTO;
import org.service.client_person.person.domain.port.out.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonReactiveRouterTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IPersonRepository repository;

    @Test
    void testPersonReactiveRouter() {
        PersonReactiveRouter router = new PersonReactiveRouter();
        assertNotNull(router);
    }

    @Test
    void testCreatePersonOk() {
        PersonDTO requestPersonDTO = buildRequestPersonDTO();
        PersonDTO savedPersonDTO = buildSavedPersonDTO(requestPersonDTO);

        Mockito.when(repository.create(requestPersonDTO)).thenReturn(savedPersonDTO);

        webTestClient.post()
                .uri("/person")
                .body(Mono.just(requestPersonDTO), PersonDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PersonDTO.class)
                .consumeWith(result-> {
                    PersonDTO responsePersonDTO = result.getResponseBody();
                    assertNotNull(responsePersonDTO);
                    assertNull(requestPersonDTO.id());
                    assertNotNull(responsePersonDTO.id());
                    assertEquals(requestPersonDTO.identification(), responsePersonDTO.identification());
                });
    }

    private static PersonDTO buildRequestPersonDTO() {
        return new PersonDTO(null, "person1", "M", 18, "123456789", "NA", "1800100100");
    }

    private static PersonDTO buildSavedPersonDTO(PersonDTO requestPersonDTO) {
        return new PersonDTO(UUID.randomUUID(), requestPersonDTO.name(), requestPersonDTO.gender(),
                        requestPersonDTO.age(), requestPersonDTO.identification(), requestPersonDTO.address(),
                        requestPersonDTO.phone());
    }
}