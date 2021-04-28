package com.test.autorestdocs;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.test.StepVerifier;

/**
 *
 * @author Martina Ciefova
 */
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private ServiceClass service;

    @Mock
    private Repository repository;

    @Mock
    private ModelMapper mapper;

    /**
     * Test of findEntity method, of class Service.
     */
    @Test
    public void testFindEntity() {
        Long id = 16L;
        String name = "name";
        Integer code = 200;

        Model model = new Model();
        model.setName(name);
        model.setCode(code);

        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(model));

        Response response = new Response();
        response.setId(id);
        response.setName(name);
        response.setCode(code);

        when(mapper.map(any(Model.class), any(Class.class)))
                .thenReturn(response);

        StepVerifier.create(service.findEntity(id))
                .expectNext(response)
                .verifyComplete();
    }

    /**
     * Test of createEntity method, of class Service.
     */
    @Test
    public void testCreateEntity() {
        Long id = 16L;
        String name = "name";
        Integer code = 200;

        Model model = new Model();
        model.setName(name);
        model.setCode(code);

        when(mapper.map(any(Request.class), eq(Model.class)))
                .thenReturn(model);

        Response response = new Response();
        response.setId(id);
        response.setName(name);
        response.setCode(code);

        when(mapper.map(any(Model.class), eq(Response.class)))
                .thenReturn(response);

        StepVerifier.create(service.createEntity(new Request()))
                .expectNext(response)
                .verifyComplete();
    }

}
