package com.jourgeois.backend.service.auth;

import com.jourgeois.backend.Utils.auth.MailAuthUtil;
import com.jourgeois.backend.domain.auth.EmailToken;
import com.jourgeois.backend.dto.EmailAuthForm;
import com.jourgeois.backend.repository.auth.EmailTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {
    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;
    private final MailAuthUtil mailAuthUtil;

    @Autowired
    EmailService(EmailTokenRepository emailTokenRepository, EmailSenderService emailSenderService, MailAuthUtil mailAuthUtil) {
        this.emailTokenRepository = emailTokenRepository;
        this.emailSenderService = emailSenderService;
        this.mailAuthUtil = mailAuthUtil;
    }

    @Transactional
    public boolean sendVerifyEmail(EmailAuthForm emailAuthForm) throws Exception {
        String email = emailAuthForm.getEmail();
        Assert.notNull(email, "이메일 주소는 필수입니다.");

        // 이메일 인증 토큰 생성
        EmailToken emailToken = MailAuthUtil.createEmailToken(email);

        emailTokenRepository.save(emailToken);

        // 회원 가입 인증 이메일 전송
        MimeMessage mailMessage = mailAuthUtil.createMessage(emailToken, emailAuthForm.getEmail());

        emailSenderService.sendEmail(mailMessage);

        return true;
    }

    @Transactional
    public boolean verifyEmail(EmailAuthForm authForm) throws Exception {
        String email = authForm.getEmail();
        String token = authForm.getToken();
        Optional<EmailToken> validEmailToken = emailTokenRepository.findByIdAndTokenAndExpirationDateAfterAndExpiredAndVerified(email, token, LocalDateTime.now(), false, false);

        MailAuthUtil.setTokenToVerified(validEmailToken.orElseThrow(() -> new Exception("유효한 토큰 없음")));

        return !validEmailToken.isEmpty();
    }

    @Transactional
    public boolean checkVerified(String userEmail) throws Exception {
        Optional<EmailToken> verifiedEmailToken = emailTokenRepository.findByIdAndExpiredAndVerified(userEmail, false, true);

        MailAuthUtil.setTokenToExpired(verifiedEmailToken.orElseThrow(() -> new Exception("검증된 토큰 없음")));

        return !verifiedEmailToken.isEmpty();
    }
}
