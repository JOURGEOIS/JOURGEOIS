package com.jourgeois.backend.service;

import com.jourgeois.backend.domain.auth.EmailToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String EMAIL_TOKEN = "emailToken:";
    public final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public boolean setToken(EmailToken emailToken) throws Exception {
        String key = EMAIL_TOKEN + emailToken.getEmail();
        redisTemplate.opsForValue().set(key, emailToken);
        redisTemplate.expire(key, 3, TimeUnit.MINUTES);
        System.out.println(emailToken + "성공");
        return true;
    }

    @Transactional
    public Optional<EmailToken> getToken(String email) throws Exception {
        String key = EMAIL_TOKEN + email;
        EmailToken emailToken = (EmailToken) redisTemplate.opsForValue().get(key);
        System.out.println(emailToken + "성공");
        return Optional.of(emailToken);
    }

    public boolean deleteToken(String email) throws Exception {
        String key = EMAIL_TOKEN + email;
        return  redisTemplate.delete(key);
    }
}