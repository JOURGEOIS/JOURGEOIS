package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.post.PostDTO;
import com.jourgeois.backend.service.CocktailAwardsService;
import com.jourgeois.backend.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/awards")
public class CocktailAwardsController {
    private final CocktailAwardsService cocktailAwardsService;
    private final PostService postService;

    public CocktailAwardsController(CocktailAwardsService cocktailAwardsService, PostService postService) {
        this.cocktailAwardsService = cocktailAwardsService;
        this.postService = postService;
    }

    @PostMapping("/auth")
    public ResponseEntity postAwards(HttpServletRequest request, @ModelAttribute PostDTO post) {
        Map<String, String> result = new HashMap<>();

        // 사진 필수 입력
        if(post.getImg() == null || post.getImg().isEmpty()) {
            result.put("fail", "이미지를 등록해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        // title 필수 입력
        if(post.getTitle() == null || post.getTitle().isEmpty()){
            result.put("fail", "이름을 등록해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        try{
            if(post.getAgree()){
                Long uid = Long.valueOf((String) request.getAttribute("uid"));
                post.setUid(uid);
                if(!cocktailAwardsService.postCheck(uid)){
                    result.put("fail", "이미 참여한 회원입니다.");
                    return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity(cocktailAwardsService.postAwards(post), HttpStatus.CREATED);
            }
            result.put("fail", "개인정보를 동의해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch(NumberFormatException e){
            result.put("fail", "uid의 형식이 다릅니다.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch(IllegalArgumentException e){
            result.put("fail", "필수 입력 정보를 기입해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch(IOException e) {
            result.put("fail", "파일 업로드 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            result.put("fail", "글 등록 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity postAwardsCheck(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            if(!cocktailAwardsService.postCheck(uid)){
                return new ResponseEntity(false, HttpStatus.OK);
            }
            return new ResponseEntity(true, HttpStatus.OK);
        } catch(NumberFormatException e){
            result.put("fail", "uid의 형식이 다릅니다.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getCocktailAwardsPostList(@RequestHeader(value = "uid", defaultValue = "0") Long uid,
                                                    @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, String> result = new HashMap<>();
        try{
            return new ResponseEntity(cocktailAwardsService.getCocktailAwardsPostList(uid, pageable), HttpStatus.OK);
        } catch(Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/result")
    public ResponseEntity getCocktailAwardsVoteList(@PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, String> result = new HashMap<>();
        try{
            return new ResponseEntity(cocktailAwardsService.getCocktailAwardsVoteList(pageable), HttpStatus.OK);
        } catch(Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth/info")
    public ResponseEntity getCocktailAwardsPostInfo(HttpServletRequest request,
                                                    @RequestParam(value = "postId") Long postId){
        Map<String, String> result = new HashMap<>();
        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(cocktailAwardsService.getCocktailAwardsPostInfo(uid, postId), HttpStatus.OK);
        } catch(Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/auth/like")
    public ResponseEntity pushPostBookmark(HttpServletRequest request, @RequestBody Map<String, Long> bookmark){
        Map<String, Integer> data = new HashMap<>();
        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            bookmark.put("uid", uid);
            if(postService.checkPostId(bookmark.get("postId"))) {
                if (postService.pushBookmark(bookmark)) {
                    data.put("status", 1);
                } else {
                    data.put("status", 0);
                }
                data.put("count", postService.countPostBookmark(bookmark.get("postId")));
                return new ResponseEntity<>(data, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not Found" ,  HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
