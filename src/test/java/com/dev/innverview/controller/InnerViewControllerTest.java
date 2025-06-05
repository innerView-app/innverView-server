package com.dev.innverview.controller;

import com.dev.innverview.domain.InnerViewRepository;
import com.dev.innverview.domain.InnerViewType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration"
})
@org.springframework.context.annotation.Import(com.dev.innverview.TestSecurityConfig.class)
@AutoConfigureMockMvc
@org.springframework.boot.test.mock.mockito.MockBean(com.dev.innverview.service.CustomOAuth2UserService.class)
@org.springframework.boot.test.mock.mockito.MockBean(org.springframework.security.oauth2.client.OAuth2AuthorizedClientService.class)
@org.springframework.test.context.ActiveProfiles("test")
class InnerViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    InnerViewRepository repository;

    @Test
    void createAndGetInnerView() throws Exception {
        InnerViewController.InnerViewRequest req = new InnerViewController.InnerViewRequest();
        req.setTitle("test view");
        req.setType(InnerViewType.YEAR);

        String response = mockMvc.perform(post("/api/innerviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/api/innerviews/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test view"));

        assertThat(repository.findAll()).hasSize(1);
    }
}
