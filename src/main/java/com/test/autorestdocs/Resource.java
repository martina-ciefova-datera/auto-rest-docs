package com.test.autorestdocs;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@RestController
@RequestMapping("/test")
@Validated
public class Resource {

    /**
     * Returns entity with a required id.
     * @param id entity id
     * @return entity
     */
    @GetMapping("/{id}")
    public Mono<Response> getEntity(@PathVariable Long id){
        return Mono.just(createResponse(id));
    }

    @PostMapping
    public Mono<Response> postEntity(@Valid @RequestBody Request request) {
        return Mono.just(createResponse(request));
    }

    private Response createResponse(Long id){
        Response r = new Response();
        r.setId(id);
        r.setCode(100);
        r.setName("name");

        return r;
    }

    private Response createResponse(Request request) {
        Response r = new Response();
        r.setId(10L);
        r.setCode(request.getCode());
        r.setName(request.getName());

        return r;
    }
}
