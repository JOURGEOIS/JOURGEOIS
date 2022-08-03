package com.jourgeois.backend.repository.auth;


import com.jourgeois.backend.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUid(Long uid);
    void deleteByUid(Long uid);

}
