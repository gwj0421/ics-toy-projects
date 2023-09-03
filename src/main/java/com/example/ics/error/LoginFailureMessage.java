package com.example.ics.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public enum LoginFailureMessage {
    USERNAME_NOT_FOUND(UsernameNotFoundException.class,"사용자 이름(아이디) 혹은 비밀번호가 잘못됨"),
    BAD_CREDENTIALS(BadCredentialsException.class,"사용자 이름(아이디) 혹은 비밀번호가 잘못됨"),
    LOCKED(LockedException.class,"계정이 잠김"),
    DISABLED(DisabledException.class,"계정이 비활성화됨"),
    ACCOUNT_EXPIRED(AccountExpiredException.class,"계정의 유효 기간 만료"),
    CREDENTIALS_EXPIRED(CredentialsExpiredException.class,"계정의 비밀번호 유효 기간 만료"),
    AUTH_SERVICE_EXCEPTION(AuthenticationServiceException.class,"인증 서비스에 문제 발생");

    private static final Map<Class<? extends AuthenticationException>, String> exceptionMap = new HashMap<>();

    static {
        for (LoginFailureMessage failureMessage : values()) {
            exceptionMap.put(failureMessage.exception, failureMessage.desc);
        }
    }
    private Class<? extends AuthenticationException> exception;
    private String desc;

    LoginFailureMessage(Class<? extends AuthenticationException> exception, String desc) {
        this.exception = exception;
        this.desc = desc;
    }

    public static String getDescription(Class<? extends AuthenticationException> exceptionClass) {
        log.info("gwj : getDesc");
        String desc = exceptionMap.get(exceptionClass);
        if (desc != null) {
            return desc;
        }
        return "unexpected error";
    }
}
