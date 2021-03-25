package com.test.autorestdocs;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Martina Ciefova
 */
public class Response extends Request implements Serializable {

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
