package com.example.ics.oauth.filter;

import com.example.ics.oauth.dto.token.AuthToken;
import com.example.ics.oauth.dto.token.AuthTokenProvider;
import com.example.ics.utils.HeaderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // HTTP 요청에서 Access Token을 가져옵니다.
        String tokenStr = HeaderUtil.getAccessToken(request);

        if (tokenStr != null) {
            // Access Token을 이용하여 AuthToken 객체로 변환합니다.
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);

            // AuthToken이 유효한지 확인하고, 유효하다면 인증을 가져온 후 SecurityContext에 설정합니다.
            if (token.validate()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 다음 필터로 체인을 전달합니다.
        filterChain.doFilter(request, response);
    }
}


