package com.jourgeois.backend.controller;

import com.amazonaws.AmazonClientException;
import com.jourgeois.backend.api.dto.cocktail.CocktailBookmarkDTO;
import com.jourgeois.backend.api.dto.post.PostBookmarkDTO;
import com.jourgeois.backend.api.dto.post.PostDTO;
import com.jourgeois.backend.api.dto.post.PostReviewDTO;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.service.CocktailService;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
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
    private final MemberService memberService;
    private final CocktailService cocktailService;

    @Autowired
    public PostController(PostService postService, MemberService memberService, CocktailService cocktailService) {
        this.postService = postService;
        this.memberService = memberService;
        this.cocktailService = cocktailService;
    }

    @PostMapping
    public ResponseEntity postPost(@ModelAttribute PostDTO post) {
        System.out.println("Request: " + post.toString());

        Map<String, String> result = new HashMap<>();

        if(post.getImg() == null || post.getImg().isEmpty()) {
            result.put("fail", "이미지를 등록해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

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

        if(post.getImg() == null || post.getImg().isEmpty()) {
            result.put("fail", "이미지를 등록해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        
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

    // 원본칵테일의 커스텀칵테일 탭 (리스트반환)
    @GetMapping
    public ResponseEntity readCustomCocktailList(@RequestParam Long id,
                                   @PageableDefault(size=10) Pageable pageable){
        try{
            return  new ResponseEntity(postService.readCumstomCoctailList(id, pageable), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity("리스트를 불러오지 못했습니다.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    // 댓글
    @PostMapping("/review")
    public ResponseEntity postReview(@RequestBody PostReviewDTO postReviewDTO) {
        System.out.println("Request: " + postReviewDTO.toString());

        Map<String, String> result = new HashMap<>();

        if(postReviewDTO.getReview() == null || postReviewDTO.getReview().isEmpty()) {
            result.put("fail", "내용을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        try{
            postService.postReview(postReviewDTO);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/review")
    public ResponseEntity editReview(@RequestBody PostReviewDTO postReviewDTO) {
        System.out.println("Request: " + postReviewDTO.toString());

        Map<String, String> result = new HashMap<>();
        if(postReviewDTO.getReview() == null || postReviewDTO.getReview().isEmpty()) {
            result.put("fail", "내용을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        try{
            postService.editReview(postReviewDTO);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            result.put("fail", "유저가 작성한 댓글이 없습니다.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            result.put("fail", "올바른 입력값을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/review")
    public ResponseEntity deleteReview(@RequestBody Map<String, Long> postDeleteReq) {
        System.out.println("Request: " + postDeleteReq.toString());

        Map<String, String> result = new HashMap<>();
        try{
            postService.deleteReview(postDeleteReq);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            result.put("fail", "유저가 작성한 댓글이 없습니다.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            result.put("fail", "올바른 입력값을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/review")
    public ResponseEntity getReviewAll(@RequestParam(value = "p_id") Long p_id,
                                        @RequestParam(value = "asc", defaultValue = "false") Boolean asc,
                                        @PageableDefault(size=10, page = 0) Pageable pageable){
        System.out.println("p_id: " + p_id);

        Map<String, String> result = new HashMap<>();
        if (p_id == null){
            result.put("fail", "잘못된 입력");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity(postService.getReviewAll(p_id, asc, pageable), HttpStatus.OK);
        } catch (Exception e) {
            result.put("fail", "댓글을 불러오는 것을 실패했습니다.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 원본 칵테일 북마크 (token 필요) => uid로 전체 바꿀 때 바꾼 후 적용 필요
    @PostMapping(value = "/bookmark")
    public ResponseEntity pushPostBookmark(@RequestBody Map<String, Long> bookmark){
        Map<String, Long> data = new HashMap<>();
        if(memberService.checkUserUid(bookmark.get("uid")) && postService.checkPostId(bookmark.get("p_id"))) {
            if (postService.pushBookmark(bookmark)) {
                data.put("status", 1L);
            } else {
                data.put("status", 0L);
            }
            data.put("count", postService.countPostBookmark(bookmark.get("p_id")));
            return new ResponseEntity<>(data, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("잘못된 회원이거나 게시물입니다.", HttpStatus.NOT_ACCEPTABLE);
        }


    }
}
