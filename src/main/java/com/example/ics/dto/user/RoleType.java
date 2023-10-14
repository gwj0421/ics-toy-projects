package com.example.ics.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN","관리자 권한"),
    USER("ROLE_USER", "일반 사용자 권한"),
    GUEST("ROLE_GUEST", "게스트 권한");

    private final String code;
    private final String description;

    private static final Map<String, RoleType> roleTypeMap = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(RoleType::getCode, Function.identity()))
    );

    public static RoleType find(String code) {
        return Optional.ofNullable(roleTypeMap.get(code)).orElse(GUEST);
    }
}