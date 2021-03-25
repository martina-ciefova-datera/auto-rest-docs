package com.test.autorestdocs;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Martina Ciefova
 */
public class Request implements Serializable {

    @NotNull
    private Integer code;

    @NotBlank
    @Size(max = 20)
    private String name;

    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
