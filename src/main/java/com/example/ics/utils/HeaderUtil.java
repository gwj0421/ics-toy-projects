package com.example.ics.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HeaderUtil {
    private static final  String HEADER_AUTHORIZATION = "Authorization";
    private static final  String TOKEN_PREFIX = "Bearer ";

    private HeaderUtil() {
        throw new IllegalStateException("Making HeaderUtil utility class error");
    }

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
