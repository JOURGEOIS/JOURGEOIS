package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.SearchHistoryDto;
import com.jourgeois.backend.api.dto.SearchTrendDto;
import com.jourgeois.backend.domain.auth.EmailToken;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String EMAIL_TOKEN = "emailToken:";
    private static final String RECENT_KEYWORD = "recentKeyword:";
    private static final String HOT_KEYWORD = "hotKeyword:";
    public final RedisTemplate<String, Object> redisTemplate;

    public final RedisTemplate<String, String> redisStringTemplate;

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

    public boolean setRecentKeyword(String email, String keyword) throws Exception {
        String key = RECENT_KEYWORD + email;
        redisTemplate.opsForZSet().add(key, keyword, System.nanoTime());
        System.out.println(key + "성공");
        return true;
    }

    @Transactional
    public Object[] getRecentKeyword(String email) throws Exception {
        String key = RECENT_KEYWORD + email;

        Set<ZSetOperations.TypedTuple<String>> res = redisStringTemplate.opsForZSet().reverseRangeWithScores(key, 0, 4);
        redisTemplate.opsForZSet().removeRange(key, -5, -5);

        System.out.println(key + "성공");
        return res.toArray();
    }

    public boolean setHotKeywords(String timepoint, SearchTrendDto searchTrendDto) {
        String key = HOT_KEYWORD + timepoint;
        redisTemplate.opsForValue().set(key, searchTrendDto);
        System.out.println("저장 성공");
        return true;
    }
    public SearchTrendDto getHotKeywords(String timepoint) throws Exception {
        String key = HOT_KEYWORD + timepoint;
        SearchTrendDto searchTrend = (SearchTrendDto) redisTemplate.opsForValue().get(key);
        System.out.println("가져오는 것 까지는 오키이이이ㅣ");
        System.out.println("조회 성공");
        return searchTrend;
    }
}