package com.jourgeois.backend.util.auth;

import com.jourgeois.backend.api.dto.EmailAuthForm;
import com.jourgeois.backend.domain.auth.EmailToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class MailAuthUtil {

    private static final String EMAIL_AUTH_PAGE = "http://localhost:8080/auth/email";

    private static final int TOKEN_LENGTH = 31;

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 3L;

    public static EmailToken createEmailToken(EmailAuthForm emailAuthForm) {
        EmailToken emailToken = new EmailToken();
        emailToken.setId(emailAuthForm.getEmail());
        emailToken.setExpirationDate(LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE));
        emailToken.setToken(createKey(TOKEN_LENGTH));
        emailToken.setExpired(false);
        emailToken.setVerified(false);
//        emailToken.setMemberId(emailAuthForm.getUserId());

        return emailToken;
    }

    public static void setTokenToExpired(EmailToken emailToken) {
        emailToken.setExpired(true);
    }

    public static void setTokenToVerified(EmailToken emailToken) { emailToken.setVerified(true);}

    public MimeMessage createMessage(EmailToken emailToken)throws Exception{
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, emailToken.getId()); //보내는 대상
        message.setSubject("주류쥬아 이메일 인증"); //제목

        StringBuffer msg = new StringBuffer();
        msg.append("<div style=\"width: 600px; border: 3px solid #202A43; padding: 2em; border-radius: 20px; background-color: #202A43;\">");
        msg.append("<img width=\"100\" height=\"100\" src=\"https://media.discordapp.net/attachments/992019732951748678/998773846800867348/icon3.png\" alt=\"\" loading=\"lazy\">");
        msg.append("<br>");
        msg.append("<strong style=\"color: #FFFFFF; font-size: 30px;\">안녕하세요, 주류주아 입니다.</strong>");
        msg.append("<p style=\"color: #FFFFFF; font-size: 16px;\">주류주아 계정 보안을 위하여 본인 확인이 필요해요.");
        msg.append("<br>");
        msg.append("아래 버튼을 클릭하여 이메일 인증을 완료해주세요.");
        msg.append("</p>");
        msg.append("<br>");
        msg.append("<div style=\"width: 350px; line-height: 48px; text-align: center; border-radius: 3px; background-color: #7979F7; cursor: pointer; margin-bottom: 20px;\">");
        msg.append("<a href=\"").append(EMAIL_AUTH_PAGE)
                .append("?token=").append(emailToken.getToken()).append("&email=").append(emailToken.getId())/*.append("&id=").append(emailToken.getMemberId())*/.append("\" style=\"padding-left: 30px; padding-right: 30px; text-decoration: none;\" target=\"_blank\" rel=\"noreferrer noopener\">");
        msg.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 16px; font-weight: 500; color: #ffffff; cursor: pointer;\">");
        msg.append("이메일 인증하고 주류주아 시작하기");
        msg.append("</span>");
        msg.append("</a>");
        msg.append("</div>");
        msg.append("<hr>");
        msg.append("<p style=\"color: #FFFFFF; \">도움이 필요하시면 jourgeois.ad@gmail.com 로 연락주세요.<p>");
        msg.append("<p style=\"color: #FFFFFF; \">- 주류주아 드림 -<p>");
        msg.append("</div>");

        message.setText(msg.toString(), "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("jourgeois.ad@gmail.com","주류주아")); //보내는 사람

        return message;
    }

    public static String createKey(int size) {
        Random random = new Random();

        int leftLimit = 48; // 0
        int rightLimit = 122; // z

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
