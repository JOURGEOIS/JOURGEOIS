package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.EmailAuthForm;
import com.jourgeois.backend.service.auth.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/email/auth")
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
            return success ? new ResponseEntity(data, HttpStatus.OK) : new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/confirmed")
    public @ResponseBody ResponseEntity checkVerified(@RequestHeader String email) {
        boolean verified = false;
        Map<String, Boolean> data = new HashMap<>();
        try {
            verified = emailService.checkVerified(email);
            data.put("success", verified);
            return verified ? new ResponseEntity(data, HttpStatus.OK) : new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
