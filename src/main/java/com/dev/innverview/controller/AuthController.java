package com.dev.innverview.controller;

import com.dev.innverview.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;

    @Value("${kakao.client-id}")
    String kakaoRestApiKey;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @GetMapping("/auth/kakao")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakaoRestApiKey+"&redirect_uri="+kakaoRedirectUri;
        model.addAttribute("location", location);
        return "login";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code) throws UnsupportedEncodingException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        return "redirect:/";
    }

    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code) {
        // 카카오 API를 통해 Access Token 및 사용자 정보 가져오기
        System.out.println("인가 코드: " + code);
        return "redirect:/";
    }
}
