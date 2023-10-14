package com.example.ics.service;

import com.example.ics.config.properties.AppProperty;
import com.example.ics.dto.user.SiteUser;
import com.example.ics.oauth.dto.auth.AuthReqModel;
import com.example.ics.oauth.dto.token.AuthToken;
import com.example.ics.oauth.dto.token.AuthTokenProvider;
import com.example.ics.oauth.dto.token.UserRefreshToken;
import com.example.ics.oauth.repository.UserRefreshTokenRepository;
import com.example.ics.oauth.utils.CookieUtil;
import com.example.ics.repository.SiteUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.example.ics.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2Service {
    private final AuthTokenProvider tokenProvider;
    private final AppProperty appProperty;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository userRepository;

    public SiteUser oAuth2Login(HttpServletRequest request, HttpServletResponse response, String token) {
        AuthReqModel authReqModel = getAuthReqModelByToken(token);
        processLogin(request, response, authReqModel);
        return getSiteUserByToken(token);
    }

    private AuthReqModel getAuthReqModelByToken(String token) {
        AuthToken authToken = tokenProvider.convertAuthToken(token);
        String id = authToken.getTokenClaims().getSubject();
        return new AuthReqModel(id, id);
    }

    private SiteUser getSiteUserByToken(String token) {
        AuthToken authToken = tokenProvider.convertAuthToken(token);
        String id = authToken.getTokenClaims().getSubject();
        Optional<SiteUser> user = userRepository.findSiteUserByUserId(id);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response, AuthReqModel authReqModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );
        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();

        long refreshTokenExpiry = appProperty.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperty.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findUserRefreshTokenByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
    }
}
