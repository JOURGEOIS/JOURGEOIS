package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.chat.ChatMessageDTO;
import com.jourgeois.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity loadChatRoomTest(){
        Map<String, Object> result = new HashMap<>();
        try {
            return new ResponseEntity(chatService.loadChatRoom(123123L), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/send")
    public String send(@RequestBody ChatMessageDTO chatMessageDTO){
        Map<String, Object> result = new HashMap<>();
        try {
            chatService.sendMessage(chatMessageDTO);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
