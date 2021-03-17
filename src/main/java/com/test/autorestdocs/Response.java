package com.test.autorestdocs;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Martina Ciefova
 */
public class Response extends Request {

    @NotNull
    private Long id;

    public Response() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
