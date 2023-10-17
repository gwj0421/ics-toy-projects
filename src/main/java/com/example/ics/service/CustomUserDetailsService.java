package com.example.ics.service;

import com.example.ics.dto.user.SiteUser;
import com.example.ics.dto.user.UserPrincipal;
import com.example.ics.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SiteUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> user = this.userRepository.findSiteUserByUserId(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없다");
        }
        return UserPrincipal.create(user.get());
    }
}
