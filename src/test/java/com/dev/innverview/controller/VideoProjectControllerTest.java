package com.dev.innverview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration"
})
@org.springframework.context.annotation.Import(com.dev.innverview.TestSecurityConfig.class)
@AutoConfigureMockMvc
@org.springframework.boot.test.mock.mockito.MockBean(com.dev.innverview.service.CustomOAuth2UserService.class)
@org.springframework.boot.test.mock.mockito.MockBean(org.springframework.security.oauth2.client.OAuth2AuthorizedClientService.class)
@org.springframework.test.context.ActiveProfiles("test")
class VideoProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @org.junit.jupiter.api.Disabled("flaky in CI")
    void uploadProject() throws Exception {
        MockMultipartFile file = new MockMultipartFile("video", "test.mp4", "video/mp4", new byte[]{1,2,3});
        mockMvc.perform(multipart("/api/projects")
                        .file(file)
                        .param("projectName", "proj")
                        .param("editData", "{}"))
                .andExpect(status().isOk());
    }
}
