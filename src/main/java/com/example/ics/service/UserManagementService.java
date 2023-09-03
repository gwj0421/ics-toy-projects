package com.example.ics.service;

import com.example.ics.dto.user.SiteUser;
import com.example.ics.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManagementService{
    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository userRepository;

    @Value("${user.clova.maxCnt}")
    private int clovaRemainingCnt;

    @Value("${user.chatGPT.maxCnt}")
    private int chatGTPRemainingCnt;

    @Value("${user.initIsAccountNonExpired}")
    private boolean initIsAccountNonExpired;

    @Value("${user.initIsAccountNonLocked}")
    private boolean initIsAccountNonLocked;

    @Value("${user.initIsCredentialsNonExpired}")
    private boolean initIsCredentialsNonExpired;

    @Value("${user.initIsEnabled}")
    private boolean initIsEnabled;

    @Value("${user.initRole}")
    private String initRole;

    public static boolean checkBothPasswordNotEqual(String pw1, String pw2) {
        if (!pw1.equals(pw2)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public SiteUser create(String username, String userID, String password, String email) {
        SiteUser user = SiteUser.builder()
                .name(username)
                .userID(userID)
                .userPassword(passwordEncoder.encode(password))
                .email(email)
                .isAccountNonExpired(initIsAccountNonExpired)
                .isAccountNonLocked(initIsAccountNonLocked)
                .isCredentialsNonExpired(initIsCredentialsNonExpired)
                .isEnabled(initIsEnabled)
                .clovaRemainingCnt(clovaRemainingCnt)
                .chatGTPRemainingCnt(chatGTPRemainingCnt)
                .role(initRole)
                .build();
        this.userRepository.save(user);
        return user;
    }

    public List<SiteUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserDatabase() {
        this.userRepository.cleanup();
    }
}
