package com.test.autorestdocs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author Martina Ciefova
 */
@Service
public class ServiceClass {

    private final Repository repository;
    private final ModelMapper mapper;

    @Autowired
    public ServiceClass(Repository repository,
                        ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<Response> findEntity(Long id) {
        Model entity = repository.findById(id).orElse(null);
        Response response = mapper.map(entity, Response.class);

        return Mono.just(response);
    }

    public Mono<Response> createEntity(Request request) {
        Model entity = mapper.map(request, Model.class);
        repository.save(entity);

        return Mono.just(mapper.map(entity, Response.class));
    }
}
