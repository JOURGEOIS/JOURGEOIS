package com.jourgeois.backend.controller;

import com.amazonaws.AmazonClientException;
import com.jourgeois.backend.api.dto.PostDTO;
import com.jourgeois.backend.api.dto.ProfileDTO;
import com.jourgeois.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            result.put("fail", "글 등록 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity editPost(@ModelAttribute PostDTO post) {
        System.out.println("Request: " + post.toString());

        Map<String, String> result = new HashMap<>();
        try{
            postService.editPost(post);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (IOException e) {
            result.put("fail", "파일 업로드 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            result.put("fail", "게시글이 존재하지 않음");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tmp")
    public ResponseEntity postImageTempStorage(@ModelAttribute PostDTO postDTO){
        Map<String, String> data = new HashMap<>();
        try{
            data.put("url", "http://13.209.206.237/img/" + postService.postImageLocalUpload(postDTO));
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e) {
            data.put("fail", "이미지 업로드 실패");
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping
    public ResponseEntity deletePost(@RequestBody Map<String, Long> postDeleteReq) {
        Map<String, String> result = new HashMap<>();
        try {
            postService.deletePost(postDeleteReq);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (AmazonClientException e) {
            System.out.println(e);
            result.put("fail", "사진 삭제 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            System.out.println(e);
            result.put("fail", "게시글이 존재하지 않음");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
