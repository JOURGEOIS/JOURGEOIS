package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.post.PostDTO;
import com.jourgeois.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PutMapping("/auth/read")
    public ResponseEntity readNotification(HttpServletRequest request, @RequestBody Map<String, String> notificationId){
        Map<String, Object> result = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            result.put("success", notificationService.changeToBeRead(uid, notificationId));
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/auth/readall")
    public ResponseEntity readNotificationAll(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            result.put("success", notificationService.changeToBeReadAll(uid));
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth/list")
    public ResponseEntity getNotificationList(HttpServletRequest request, @RequestParam(value="page") int page){
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(notificationService.getNotificationList(uid, page), HttpStatus.OK);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
