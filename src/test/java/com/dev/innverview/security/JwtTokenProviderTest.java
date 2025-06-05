package com.dev.innverview.security;

import com.dev.innverview.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private final JwtTokenProvider provider = new JwtTokenProvider();

    @Test
    void generateAndValidateToken() {
        provider.init();
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .username("tester")
                .build();
        String token = provider.generateToken(user);
        assertThat(provider.validateToken(token)).isTrue();
        assertThat(provider.getUserId(token)).isEqualTo(1L);
        assertThat(provider.getUsername(token)).isEqualTo("tester");
        assertThat(provider.getSubject(token)).isEqualTo("test@example.com");
    }
}
