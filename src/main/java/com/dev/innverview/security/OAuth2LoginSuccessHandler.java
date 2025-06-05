package com.dev.innverview.security;

import com.dev.innverview.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = extractEmail(oAuth2User);
        if (email != null) {
            String token = tokenProvider.generateToken(email);
            response.addHeader("Authorization", "Bearer " + token);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String extractEmail(OAuth2User user) {
        String email = user.getAttribute("email");
        if (email == null) {
            Map<String, Object> account = user.getAttribute("kakao_account");
            if (account != null) {
                email = (String) account.get("email");
            }
        }
        return email;
    }
}
