package com.jourgeois.backend.repository.auth;

import com.jourgeois.backend.domain.auth.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    Optional<EmailToken> findByIdAndTokenAndExpirationDateAfterAndExpiredAndVerified(String email, String token, LocalDateTime now, boolean expired, boolean verified);
    Optional<EmailToken> findByIdAndExpiredAndVerified(String email, boolean expired, boolean verified);
}
