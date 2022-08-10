package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.service.MemberProfilePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/member/profilepage")
public class MemberProfilePageController {
    private final MemberProfilePageService memberProfilePageService;

    @Autowired
    MemberProfilePageController(MemberProfilePageService memberProfilePageService) {
        this.memberProfilePageService = memberProfilePageService;
    }

    @GetMapping("/auth")
    public ResponseEntity<?> readProfile(HttpServletRequest request, @ModelAttribute MemberDTO memberDTO){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long uid = Long.parseLong(((String) request.getAttribute("uid")));
            MemberDTO result = memberProfilePageService.readMemberProfile(uid);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/post")
    public ResponseEntity<?> readPost(HttpServletRequest request, @ModelAttribute MemberDTO memberDTO){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long uid = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, String>> result = memberProfilePageService.readMemberCocktailOrPost(uid, "post");

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/cocktail")
    public ResponseEntity<?> readCustomCocktail(HttpServletRequest request, @ModelAttribute MemberDTO memberDTO){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long uid = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, String>> result = memberProfilePageService.readMemberCocktailOrPost(uid, "cocktail");

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/bookmark")
    public ResponseEntity<?> readBookmark(HttpServletRequest request, @ModelAttribute MemberDTO memberDTO){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long uid = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, String>> result = memberProfilePageService.readMemberBookmark(uid);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/comment")
    public ResponseEntity<?> readCocktailComment(HttpServletRequest request, @ModelAttribute MemberDTO memberDTO){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long uid = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, String>> result = memberProfilePageService.readMemberCocktailComment(uid);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
