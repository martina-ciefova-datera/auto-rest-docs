package com.test.autorestdocs;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.webflux.WebTestClientInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 *
 * @author Martina Ciefova
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class WebTestClientTestBase {

    @Autowired
    public ApplicationContext context;

    protected WebTestClient webTestClient;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
        this.webTestClient = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost:8080/")
                .filter(documentationConfiguration(restDocumentation)
                        .snippets()
                        .withDefaults(WebTestClientInitializer.prepareSnippets(context),
                                      CliDocumentation.curlRequest(),
                                      HttpDocumentation.httpRequest(),
                                      HttpDocumentation.httpResponse(),
                                      AutoDocumentation.requestFields(),
                                      AutoDocumentation.responseFields(),
                                      AutoDocumentation.pathParameters(),
                                      AutoDocumentation.requestParameters(),
                                      AutoDocumentation.description(),
                                      AutoDocumentation.methodAndPath(),
                                      AutoDocumentation.section()))
                .build();
    }
}
