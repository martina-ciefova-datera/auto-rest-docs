package com.test.autorestdocs;

import java.util.Date;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
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

    private final Repository repository;

    private final ModelMapper mapper;

    @Autowired
    public Resource(TestProperties properties, Repository repository,
                    ModelMapper mapper) {
        this.properties = properties;
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns entity with a required id.
     *
     * @param id entity id
     * @return entity
     */
    @GetMapping("/{id}")
    public Mono<Response> getEntity(@PathVariable Long id) {
        Model entity = repository.findById(id).orElse(null);
        Response response = mapper.map(entity, Response.class);

        return Mono.just(response);
    }

    @PostMapping
    public Mono<Response> postEntity(@Valid @RequestBody Request request) {
        Model entity = mapper.map(request, Model.class);
        repository.save(entity);

        return Mono.just(mapper.map(entity, Response.class));
    }

    @GetMapping("/now")
    public Mono<Date> getEntity() {
        return Mono.just(new Date());
    }
}
