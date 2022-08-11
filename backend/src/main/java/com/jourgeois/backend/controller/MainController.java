package com.jourgeois.backend.controller;

import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin("*") // CORS 설정, 배포 시에 변경 필요
public class MainController {

    private final NotificationService notificationService;

    @Autowired
    MainController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value="/")
    public String mainControl() throws Exception {
        Member from = new Member();
        from.setUid(123123L);
        from.setNickname("king");

        for(int i = 0; i < 30; i++) {
            Member to = new Member();
            to.setUid((long) i);
            to.setNickname("송선아 메롱");
            notificationService.followNotification(from, to);
        }
        System.out.println("Main Controller");
        return "main";
    }
}