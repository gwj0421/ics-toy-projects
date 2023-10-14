package com.example.ics.service;

import com.example.ics.dto.user.ProviderType;
import com.example.ics.dto.user.RoleType;
import com.example.ics.dto.user.SiteUser;
import com.example.ics.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository userRepository;

    public List<SiteUser> findAll() {
        return userRepository.findAll();
    }


    public SiteUser createUser(String userId, String username, String password, ProviderType providerType, RoleType roleType) {
        SiteUser user = new SiteUser(userId, username, false, providerType, roleType, LocalDateTime.now(), LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }


}
