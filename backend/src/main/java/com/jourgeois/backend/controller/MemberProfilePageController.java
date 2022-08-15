package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.service.MemberProfilePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/member/profile")
public class MemberProfilePageController {
    private final MemberProfilePageService memberProfilePageService;

    @Autowired
    MemberProfilePageController(MemberProfilePageService memberProfilePageService) {
        this.memberProfilePageService = memberProfilePageService;
    }

    @GetMapping("/auth")
    public ResponseEntity<?> readProfile(HttpServletRequest request, @RequestParam(value = "uid") Long uid){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long myUid = Long.valueOf((String) request.getAttribute("uid"));
            Map<String, Object> result = memberProfilePageService.readMemberProfile(myUid, uid);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/post")
    public ResponseEntity<?> readPost(HttpServletRequest request, @RequestParam(value = "uid") Long uid,
                                      @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long userId = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, Object>> result = memberProfilePageService.readMemberCocktailOrPost(userId, uid, pageable, "post");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/cocktail")
    public ResponseEntity<?> readCustomCocktail(HttpServletRequest request, @RequestParam(value = "uid") Long uid,
                                                @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long userId = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, Object>> result = memberProfilePageService.readMemberCocktailOrPost(userId, uid, pageable,"cocktail");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/bookmark")
    public ResponseEntity<?> readBookmark(HttpServletRequest request, @RequestParam(value = "uid") Long uid,
                                          @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long userId = Long.parseLong((String) request.getAttribute("uid"));
            if(userId.equals(uid)){
                List<Map<String, String>> result = memberProfilePageService.readMemberBookmark(uid, pageable);
                return ResponseEntity.ok().body(result);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/auth/comment")
    public ResponseEntity<?> readCocktailComment(HttpServletRequest request, @RequestParam(value = "uid") Long uid,
                                                 @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, Boolean> data = new HashMap<>();

        try {
            Long userId = Long.parseLong(((String) request.getAttribute("uid")));
            List<Map<String, String>> result = memberProfilePageService.readMemberCocktailComment(userId, uid, pageable);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/auth/profile-status")
    public ResponseEntity<?> switchPublicPrivate(HttpServletRequest request) {
        Long userId = Long.parseLong(((String) request.getAttribute("uid")));
        Integer isPrivate = memberProfilePageService.switchPublicToPrivate(userId);

        return ResponseEntity.ok().body(Map.of("isPrivate", isPrivate));
    }
}
