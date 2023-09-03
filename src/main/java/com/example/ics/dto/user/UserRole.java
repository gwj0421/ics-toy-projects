package com.example.ics.dto.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public static UserRole findUserRoleByValue(String value) {
        return valueOf(value.toUpperCase());
    }
}
