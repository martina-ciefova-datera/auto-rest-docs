package com.test.autorestdocs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Martina Ciefova
 */
@ConstructorBinding
@ConfigurationProperties("test")
@Validated
public class TestProperties {
    @NotBlank
    private final String value;

    @NotNull
    private final Integer count;

    public TestProperties(@Name("magic-value") String value,
                          @DefaultValue("7") Integer count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public Integer getCount() {
        return count;
    }



}
