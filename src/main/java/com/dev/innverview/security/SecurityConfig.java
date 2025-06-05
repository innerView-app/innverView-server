package com.dev.innverview.security;

import com.dev.innverview.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@org.springframework.context.annotation.Profile("!test")
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    /**
     * 정적 리소스 필터 제외 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/images/**", "/js/**", "/css/**", "/static/**", "/favicon.ico", "/error", "/swagger-ui/**");
    }

    /**
     * Spring Security Filter Chain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // OAuth2 요청 리다이렉트를 담당하는 필터 설정
        var resolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository,
                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        );
        var redirectFilter = new OAuth2AuthorizationRequestRedirectFilter(resolver);

        http.addFilterBefore(redirectFilter, OAuth2AuthorizationRequestRedirectFilter.class);

        http
                // CORS 및 CSRF 설정
                .cors(cors -> cors.disable()) // CORS 비활성화 (필요 시 별도 설정)
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (REST API에 적합)

                // 세션 관리
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession() // 세션 고정 보호
                )

                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/auth/**", "/oauth2/**", "/error").permitAll() // 공용 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 전용 경로
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint ->
                                endpoint.authorizationRequestResolver(resolver))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")) // 로그아웃 경로
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
                );

        return http.build();
    }
}
