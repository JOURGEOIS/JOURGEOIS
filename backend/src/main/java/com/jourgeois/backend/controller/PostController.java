package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.PostDTO;
import com.jourgeois.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity postPost(@ModelAttribute PostDTO post) {
        System.out.println("Request: " + post.toString());

        Map<String, String> result = new HashMap<>();
        try{
            postService.postPost(post);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (IOException e) {
            result.put("fail", "파일 업로드 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            result.put("fail", "글쓴이가 존재하지 않음");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
