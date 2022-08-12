package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/test")
    public String loadChatRoomTest(){
        Map<String, Object> result = new HashMap<>();
        try {
            chatService.loadChatRoom(123123L);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
