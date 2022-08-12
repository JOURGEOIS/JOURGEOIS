package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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

    @GetMapping("/test2")
    public ResponseEntity getChatMessages(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam("roomId") String roomId){
        // uid : /auth 추가해서 request.get("uid")로 받아서 넘겨주십시오.
        // page는 처음엔 0부터 시작이고 넘겨줄 때 size로 값을 넘겨주니, page로 값을 받으면 됨
        // roomId 가 채팅방 key
        try {
            return new ResponseEntity(chatService.getChatMessages(1L, page,"ndyaTotGrzzROpvsn1NV"), HttpStatus.OK);
        } catch (ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
