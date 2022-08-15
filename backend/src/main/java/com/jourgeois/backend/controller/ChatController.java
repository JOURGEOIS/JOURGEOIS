package com.jourgeois.backend.controller;

import com.google.cloud.Timestamp;
import com.jourgeois.backend.api.dto.chat.ChatMessageDTO;
import com.jourgeois.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/auth/chatroom")
    public ResponseEntity loadChatRoom(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
//            Long uid = 16052L;
            return new ResponseEntity(chatService.loadChatRoom(uid), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/message")
    public String send(HttpServletRequest request, @RequestBody ChatMessageDTO chatMessageDTO){
        Map<String, Object> result = new HashMap<>();
        try {
            Long myUid = Long.valueOf((String) request.getAttribute("uid"));
//            Long myUid = 80030L;
            chatMessageDTO.setSender(myUid);
            chatMessageDTO.setTimestamp(Timestamp.now());
            chatMessageDTO.setIsRead(false);
            chatService.sendMessage(chatMessageDTO);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("/auth/message")
    public ResponseEntity getChatMessages(HttpServletRequest request, @RequestParam(value = "startAfter", defaultValue = "0") Integer startAfter, @RequestParam("roomId") String roomId){
        // uid : /auth 추가해서 request.get("uid")로 받아서 넘겨주십시오.
        // page는 처음엔 0부터 시작이고 넘겨줄 때 size로 값을 넘겨주니, page로 값을 받으면 됨
        // roomId 가 채팅방 key
        try {
            Long myUid = Long.valueOf((String)request.getAttribute("uid"));
//            Long myUid = 16052L;
            return new ResponseEntity(chatService.getChatMessages(myUid, /* startAfter, */ roomId), HttpStatus.OK);
        } catch (ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
