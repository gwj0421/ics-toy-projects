package com.example.ics.oauth.siteUserInfo;


import com.example.ics.dto.user.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case GITHUB: return new GithubOAUth2UserInfo(attributes);

            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
