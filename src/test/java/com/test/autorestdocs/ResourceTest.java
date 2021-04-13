package com.test.autorestdocs;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
@ActiveProfiles({"test", "jwt", "ldap", "captcha"})
@WebFluxTest(controllers = Resource.class)
@Import(ModelMapper.class)
public class ResourceTest extends WebTestClientTestBase {

    @MockBean
    private Repository repository;

    @Test
    @WithMockUser
    public void getEntity() {
        Long id = 16L;
        String name = "name";
        Integer code = 100;

        Model model = new Model();
        model.setId(id);
        model.setCode(code);
        model.setName(name);

        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(model));

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
        Request request = new Request();
        request.setCode(200);
        request.setName("name");

        webTestClient.mutateWith(csrf()).post().uri("/test")
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
