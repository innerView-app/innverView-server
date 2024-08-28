package com.dev.innverview.security.oauth2;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("properties.nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("kakao_account.email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("properties.profile_image");
    }

}
