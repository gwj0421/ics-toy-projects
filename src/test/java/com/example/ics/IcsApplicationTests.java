package com.example.ics;

import com.example.ics.dto.Clova.ClovaContentType;
import com.example.ics.dto.user.SiteUser;
import com.example.ics.dto.user.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
class IcsApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
//        SiteUser user = SiteUser.builder()
//                .name("gwj0421")
//                .userID("gunwoong")
//                .userPassword(passwordEncoder.encode("doldol1630"))
//                .email("gwj0421@gmail.com")
//                .emailVerified(false)
//                .locked(false)
//                .clovaRemainingCnt(5)
//                .chatGTPRemainingCnt(5)
//                .role("ADMIN,USER")
//                .build();
//        List<SimpleGrantedAuthority> temp = Arrays.stream(user.getRole().split(","))
//                .map(UserRole::findUserRoleByValue)
//                .map(UserRole::getRoleName)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }


}
