package com.dev.innverview.security;

import com.dev.innverview.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private final JwtTokenProvider provider = new JwtTokenProvider();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        org.springframework.test.util.ReflectionTestUtils.setField(provider, "secret", "0123456789abcdef0123456789abcdef");
        org.springframework.test.util.ReflectionTestUtils.setField(provider, "expiration", 3600000L);
        provider.init();
    }

    @Test
    void generateAndValidateToken() {
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
