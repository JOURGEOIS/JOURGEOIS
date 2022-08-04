package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.auth.EmailAuthForm;
import com.jourgeois.backend.service.auth.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/email/cert")
@Controller
public class EmailController {

    private final EmailService emailService;

    @Autowired
    EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping()
    public @ResponseBody ResponseEntity sendMail(@RequestBody EmailAuthForm userEmail) {
        boolean success = false;
        Map<String, Boolean> data = new HashMap<>();
        try {
            success = emailService.sendVerifyEmail(userEmail);
            data.put("success", success);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/verified")
    public  @ResponseBody ResponseEntity<Object> verifyMail(@RequestBody EmailAuthForm authForm) {
        boolean success = false;
        Map<String, Boolean> data = new HashMap<>();
        try {
            success = emailService.verifyEmail(authForm);
            data.put("success", success);
            return success ? new ResponseEntity(data, HttpStatus.OK) : new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/verified")
    public  @ResponseBody ResponseEntity<Object> verifyMailTest(@RequestParam String token, @RequestParam String email) {
        EmailAuthForm authForm = new EmailAuthForm();
        authForm.setToken(token);
        authForm.setUserEmail(email);
        boolean success = false;
        Map<String, Boolean> data = new HashMap<>();
        try {
            success = emailService.verifyEmail(authForm);
            data.put("success", success);
            return success ? new ResponseEntity(data, HttpStatus.OK) : new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/confirmed")
    public @ResponseBody ResponseEntity checkVerified(@RequestBody EmailAuthForm emailAuthForm) {
        boolean verified = false;
        Map<String, Boolean> data = new HashMap<>();
        try {
            verified = emailService.checkVerified(emailAuthForm);
            data.put("success", verified);
            return verified ? new ResponseEntity(data, HttpStatus.OK) : new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
