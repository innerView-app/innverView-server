package com.dev.innverview.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 정적 리소스 필터 제외 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/images/**", "/js/**", "/css/**", "/static/**");
    }

    /**
     * Spring Security Filter Chain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/**", "/login", "/auth/**", "/login/**").permitAll() // 인증 없이 접근 가능
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                // 로그인 설정
//                .formLogin(form -> form
//                        .loginPage("/login") // 로그인 페이지 설정
//                        .loginProcessingUrl("/auth/login") // 로그인 처리 URL
//                        .defaultSuccessUrl("/", true) // 항상 루트로 이동
//                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트
//                        .permitAll()
//                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 경로
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
                // CSRF 비활성화
//                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
