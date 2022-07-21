package com.jourgeois.backend.repository.auth;


import com.jourgeois.backend.domain.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> deleteByEmail(String email);

}
