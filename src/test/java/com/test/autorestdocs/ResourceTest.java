package com.test.autorestdocs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@WebFluxTest(controllers = Resource.class)
public class ResourceTest extends WebTestClientTestBase {
    @Test
    public void getEntity() {
        webTestClient.get().uri("/test/1").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.code").isEqualTo(100)
                .jsonPath("$.name").isEqualTo("name")
                .consumeWith(document("resource/{method-name}"));
    }

    @Test
    public void postEntity() {
        Request request = new Request();
        request.setCode(200);
        request.setName("name");

        webTestClient.post().uri("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(200)
                .jsonPath("$.name").isEqualTo("name")
                .consumeWith(document("resource/{method-name}"));
    }
}
