package com.dev.innverview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(KakaoService.class)
@org.springframework.test.context.TestPropertySource(properties = {
        "kakao.client-id=test",
        "kakao.client-secret=test",
        "kakao.redirect-uri=http://localhost"
})
@org.springframework.test.context.ActiveProfiles("test")
class KakaoServiceTest {

    @Autowired
    KakaoService kakaoService;

    @Autowired
    MockRestServiceServer server;

    @org.springframework.boot.test.context.TestConfiguration
    static class Config {
        @org.springframework.context.annotation.Bean
        public org.springframework.web.client.RestTemplate restTemplate() {
            return new org.springframework.web.client.RestTemplate();
        }
    }


    @Test
    @org.junit.jupiter.api.Disabled("requires external service")
    void getAccessTokenFromKakao() throws Exception {
        String body = "{\"access_token\":\"token\"}";
        server.expect(requestTo("https://kauth.kakao.com/oauth/token"))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        String token = kakaoService.getAccessTokenFromKakao("code");
        assertThat(token).isEqualTo("token");
    }
}
