package com.jourgeois.backend.service.auth;

import com.jourgeois.backend.api.dto.EmailAuthForm;
import com.jourgeois.backend.domain.auth.EmailToken;
//import com.jourgeois.backend.repository.auth.EmailTokenRepository;
import com.jourgeois.backend.service.RedisService;
import com.jourgeois.backend.service.auth.EmailSenderService;
import com.jourgeois.backend.util.auth.MailAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final RedisService redisService;
    private final EmailSenderService emailSenderService;
    private final MailAuthUtil mailAuthUtil;

    @Transactional
    public boolean sendVerifyEmail(EmailAuthForm emailAuthForm) throws Exception {
        Assert.notNull(emailAuthForm.getEmail(), "이메일 주소는 필수입니다.");
        System.out.println(emailAuthForm.getEmail());
        // 이메일 인증 토큰 생성
        EmailToken emailToken = MailAuthUtil.createEmailToken(emailAuthForm);
        System.out.println(emailToken.getEmail());

        redisService.setToken(emailToken);

        // 회원 가입 인증 이메일 전송
        MimeMessage mailMessage = mailAuthUtil.createMessage(emailToken);
        emailSenderService.sendEmail(mailMessage);
        return true;
    }

    @Transactional
    public boolean verifyEmail(EmailAuthForm emailAuthForm) throws Exception {
        EmailToken emailToken = redisService.getToken(emailAuthForm.getEmail()).orElseThrow(() -> new NoSuchElementException("유효한 토큰 없음"));
        if(MailAuthUtil.isValidToken(emailAuthForm.getToken(), emailToken)) {
            emailToken.setVerified(true);
            redisService.setToken(emailToken);
            return true;
        } else {
            System.out.println("유효하지 않는 인증 시도입니다.");
            return false;
        }
    }

    @Transactional
    public boolean checkVerified(EmailAuthForm emailAuthForm) throws Exception {

        EmailToken emailToken = redisService.getToken(emailAuthForm.getEmail()).orElseThrow(() -> new NoSuchElementException("유효한 토큰 없음"));
        return emailToken.isVerified() ? redisService.deleteToken(emailToken.getEmail()) : false;
    }

}
