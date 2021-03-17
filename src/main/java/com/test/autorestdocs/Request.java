package com.test.autorestdocs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 *
 * @author Martina Ciefova
 */
public class Request {

    @NotNull
    private Integer code;

    @NotBlank
    @Size(max = 20)
    private String name;

    public Request() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
