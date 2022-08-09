package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.service.MemberProfilePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member/profilepage")
public class MemberProfilePageController {
    private final MemberProfilePageService memberProfilePageService;

    @Autowired
    MemberProfilePageController(MemberProfilePageService memberProfilePageService) {
        this.memberProfilePageService = memberProfilePageService;
    }

    @GetMapping("/auth")
    public ResponseEntity<?> readProfile(HttpServletRequest request, @ModelAttribute MemberDTO memberDto){
        Map<String, Boolean> data = new HashMap<>();

        try {

            memberDto.setUid(Long.valueOf(((String) request.getAttribute("uid"))));
            memberService.changeProfile(memberDto);
            return new ResponseEntity(memberService.findUserInfo(memberDto.getUid()), HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }
}
