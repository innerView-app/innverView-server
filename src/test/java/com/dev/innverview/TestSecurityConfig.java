package com.dev.innverview;

@org.springframework.context.annotation.Configuration
public class TestSecurityConfig {
    @org.springframework.context.annotation.Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
