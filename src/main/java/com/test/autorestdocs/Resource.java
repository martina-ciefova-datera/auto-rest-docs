package com.test.autorestdocs;

import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(TestProperties.class)
public class Resource {

    private final TestProperties properties;

    private final ServiceClass service;

    /**
     *
     * @param properties
     * @param service
     */
    @Autowired
    public Resource(TestProperties properties, ServiceClass service) {
        this.properties = properties;
        this.service = service;
    }

    /**
     * Loads entity.
     *
     * @param id
     * @return entity
     */
    @GetMapping("/{id}")
    public Mono<Response> getEntity(@PathVariable Long id) {
        return service.findEntity(id);
    }

    /**
     * Creates entity.
     *
     * @param request
     * @return new entity
     */
    @PostMapping
    public Mono<Response> postEntity(@Valid @RequestBody Request request) {
        return service.createEntity(request);
    }

    /**
     * Returns current time.
     *
     * @return time
     */
    @GetMapping("/now")
    public Mono<Date> getDate() {
        return Mono.just(new Date());
    }
}
