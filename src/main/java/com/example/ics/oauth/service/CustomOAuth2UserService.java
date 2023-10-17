package com.example.ics.oauth.service;

import com.example.ics.dto.user.ProviderType;
import com.example.ics.dto.user.RoleType;
import com.example.ics.dto.user.SiteUser;
import com.example.ics.dto.user.UserPrincipal;
import com.example.ics.oauth.exception.OAuthProviderMissMatchException;
import com.example.ics.oauth.info.OAuth2UserInfo;
import com.example.ics.oauth.info.OAuth2UserInfoFactory;
import com.example.ics.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Optional<SiteUser> savedUser = siteUserRepository.findSiteUserByUserId(userInfo.getId());

        if (savedUser.isPresent()) {
            SiteUser updatedUser = savedUser.get();
            if (providerType != updatedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + updatedUser.getProviderType() + " account to login."
                );
            }
            return UserPrincipal.create(updateUser(updatedUser, userInfo), user.getAttributes());

        } else {
            return UserPrincipal.create(createUser(userInfo, providerType), user.getAttributes());
        }
    }

    private SiteUser createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        SiteUser user = new SiteUser(
                userInfo.getId(),
                userInfo.getName(),
                false,
                providerType,
                RoleType.USER,
                now,
                now
        );

        user.setPassword(passwordEncoder.encode(userInfo.getId()));
        return siteUserRepository.saveAndFlush(user);
    }

    private SiteUser updateUser(SiteUser user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }
        return user;
    }
}
