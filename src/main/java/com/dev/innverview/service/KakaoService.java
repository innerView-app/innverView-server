package com.dev.innverview.service;

import com.dev.innverview.dto.KakaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    public String getAccessTokenFromKakao(String code) throws UnsupportedEncodingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<KakaoTokenResponseDto> responseEntity = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    KakaoTokenResponseDto.class
            );

            KakaoTokenResponseDto response = responseEntity.getBody();

            if (response != null) {
                log.info("Access Token: {}", response.getAccessToken());
                return response.getAccessToken();
            }
            throw new RuntimeException("Failed to retrieve Kakao token response");

        } catch (Exception e) {
            log.error("Error retrieving Kakao access token: {}", e.getMessage());
            throw new RuntimeException("Error while calling Kakao API", e);
        }
    }

}
