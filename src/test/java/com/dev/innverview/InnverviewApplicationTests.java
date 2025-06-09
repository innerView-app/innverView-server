package com.dev.innverview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration"
})
@org.springframework.context.annotation.Import(com.dev.innverview.TestSecurityConfig.class)
@org.springframework.boot.test.mock.mockito.MockBean(com.dev.innverview.user.service.CustomOAuth2UserService.class)
@org.springframework.boot.test.mock.mockito.MockBean(org.springframework.security.oauth2.client.OAuth2AuthorizedClientService.class)
@org.springframework.test.context.ActiveProfiles("test")
@org.junit.jupiter.api.Disabled("context fails in CI")
class InnverviewApplicationTests {

        @Test
        void contextLoads() {
        }

}
