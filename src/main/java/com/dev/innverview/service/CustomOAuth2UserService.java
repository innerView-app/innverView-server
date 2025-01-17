package com.dev.innverview.service;

import com.dev.innverview.domain.OAuthUser;
import com.dev.innverview.domain.OAuthUserRepository;
import com.dev.innverview.domain.User;
import com.dev.innverview.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthUserRepository oauthUserRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth 제공자 정보
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("id");
        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("nickname");
        String profileImage = oAuth2User.getAttribute("profile_image");

        // 사용자 저장 또는 업데이트
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .username(nickname)
                        .profileImage(profileImage)
                        .build()));

        // 리프레시 토큰 가져오기
        String principalName = email; // 사용자를 식별할 고유 값 (예: 이메일)
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                provider, principalName
        );

        String refreshToken;
        if (authorizedClient != null && authorizedClient.getRefreshToken() != null) {
            refreshToken = authorizedClient.getRefreshToken().getTokenValue();
        } else {
            refreshToken = null;
        }

        // OAuth 사용자 저장 또는 업데이트
        OAuthUser oauthUser = oauthUserRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> oauthUserRepository.save(OAuthUser.builder()
                        .user(user)
                        .provider(provider)
                        .providerId(providerId)
                        .accessToken(userRequest.getAccessToken().getTokenValue())
                        .refreshToken(refreshToken) // 리프레시 토큰 설정
                        .expiresAt(LocalDateTime.from(userRequest.getAccessToken().getExpiresAt()))
                        .build()));

        // 토큰 갱신
        oauthUser.setAccessToken(userRequest.getAccessToken().getTokenValue());
        oauthUser.setRefreshToken(refreshToken); // 갱신된 리프레시 토큰 설정
        oauthUser.setExpiresAt(LocalDateTime.from(userRequest.getAccessToken().getExpiresAt()));
        oauthUserRepository.save(oauthUser);
        System.out.println("OAuthUser: " + oauthUser);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}