package com.example.ics.dto.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(unique = true)
    private String userID;
    private String userPassword;
    @Column(unique = true)
    private String email;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private int clovaRemainingCnt;
    private int chatGTPRemainingCnt;

    private String role;

    @Builder
    public SiteUser(String name, String userID, String userPassword, String email, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, int clovaRemainingCnt, int chatGTPRemainingCnt, String role) {
        this.name = name;
        this.userID = userID;
        this.userPassword = userPassword;
        this.email = email;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.clovaRemainingCnt = clovaRemainingCnt;
        this.chatGTPRemainingCnt = chatGTPRemainingCnt;
        this.role = role;
    }

    private void addRole(String role) {
        this.role += "," + role;
    }
}
