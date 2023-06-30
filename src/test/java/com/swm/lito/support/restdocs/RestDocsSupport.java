package com.swm.lito.support.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swm.lito.support.security.SecuritySupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfig.class)
public abstract class RestDocsSupport extends SecuritySupport {

    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected RestDocumentationResultHandler restDocs;
    protected MockMvc mockMvc;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("https")
                        .withHost("dev.lito.com")
                        .withPort(443))
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .defaultRequest(patch("/**").with(csrf()))
                .defaultRequest(delete("/**").with(csrf()))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }
}
