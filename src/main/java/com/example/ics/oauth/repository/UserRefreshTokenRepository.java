package com.example.ics.oauth.repository;

import com.example.ics.oauth.dto.token.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findUserRefreshTokenByUserId(String userId);
    UserRefreshToken findUserRefreshTokenByUserIdAndRefreshToken(String userId,String refreshToken);

}
