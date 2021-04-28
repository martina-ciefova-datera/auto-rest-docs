package com.test.autorestdocs;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@ActiveProfiles({"test"})
@WebFluxTest(controllers = Resource.class)
//@Import(ModelMapper.class)
public class ResourceTest extends WebTestClientTestBase {

    @MockBean
    private ServiceClass service;

    @Test
    @WithMockUser
    public void getEntity() {
        Long id = 16L;
        String name = "name";
        Integer code = 100;

        Response response = new Response();
        response.setId(id);
        response.setCode(code);
        response.setName(name);

        when(service.findEntity(any(Long.class)))
                .thenReturn(Mono.just(response));

        webTestClient.get().uri("/test/1").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.code").isEqualTo(code)
                .jsonPath("$.name").isEqualTo(name)
                .consumeWith(document("resource/{method-name}"));
    }

    @Test
    @WithMockUser
    public void postEntity() {
        Long id = 16L;
        String name = "name";
        Integer code = 100;

        Request request = new Request();
        request.setCode(code);
        request.setName(name);

        Response response = new Response();
        response.setName(name);
        response.setCode(code);
        response.setId(id);

        when(service.createEntity(any(Request.class)))
                .thenReturn(Mono.just(response));

        webTestClient.mutateWith(csrf()).post().uri("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.code").isEqualTo(code)
                .jsonPath("$.name").isEqualTo(name)
                .consumeWith(document("resource/{method-name}"));
    }
}
