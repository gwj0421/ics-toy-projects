package com.example.ics.repository;

import com.example.ics.dto.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findSiteUserByUserID(String userID);

    @Modifying
    @Query(value = "DELETE FROM SITE_USER;ALTER TABLE SITE_USER AUTO_INCREMENT = 1;",nativeQuery=true)
    void cleanup();

}
