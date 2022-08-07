package com.jourgeois.backend.controller;

import com.amazonaws.AmazonClientException;
import com.jourgeois.backend.api.dto.post.PostDTO;
import com.jourgeois.backend.api.dto.post.PostReviewDTO;
import com.jourgeois.backend.service.CocktailService;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.service.PostService;
import com.jourgeois.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final SearchService searchService;

    @Autowired
    public PostController(PostService postService, SearchService searchService) {
        this.postService = postService;
        this.searchService = searchService;
    }

    @PostMapping("/auth")
    public ResponseEntity postPost(HttpServletRequest request, @ModelAttribute PostDTO post) {
        System.out.println("Request: " + post.toString());

        Map<String, String> result = new HashMap<>();

        // 사진 필수 입력
        if(post.getImg() == null || post.getImg().isEmpty()) {
            result.put("fail", "이미지를 등록해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }


        // 커스텀 칵테일의 경우 베이스 칵테일 필수 입력
        if(post.getType() != null && post.getType() == 1 && (post.getBaseCocktail() == null || post.getBaseCocktail().equals(null))){
            result.put("fail", "Base Cocktail이 없습니다.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            post.setUid(uid);
            return new ResponseEntity(postService.postPost(post), HttpStatus.CREATED);
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

    @PutMapping("/auth")
    public ResponseEntity editPost(HttpServletRequest request, @ModelAttribute PostDTO post) {
        System.out.println("Request: " + post.toString());

        Map<String, String> result = new HashMap<>();

        
        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            post.setUid(uid);
            return new ResponseEntity(postService.editPost(post), HttpStatus.CREATED);
        } catch(IllegalArgumentException e){
            result.put("fail", "필수 입력 정보를 기입해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            result.put("fail", "파일 업로드 실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            result.put("fail", "게시글이 존재하지 않음");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/tmp")
    public ResponseEntity postImageTempStorage(HttpServletRequest request, @ModelAttribute PostDTO postDTO){
        Map<String, String> data = new HashMap<>();

        if(postDTO.getImg() == null || postDTO.getImg().isEmpty()) {
            data.put("fail", "이미지를 등록해주세요.");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        }

        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            postDTO.setUid(uid);
            data.put("url", "http://13.209.206.237/img/" + postService.postImageLocalUpload(postDTO));
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e) {
            data.put("fail", "이미지 업로드 실패");
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/auth")
    public ResponseEntity deletePost(HttpServletRequest request, @RequestBody Map<String, Long> postDeleteReq) {
        Map<String, String> result = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            postDeleteReq.put("uid", uid);

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

    // 댓글
    @PostMapping("/auth/review")
    public ResponseEntity postReview(HttpServletRequest request, @RequestBody PostReviewDTO postReviewDTO) {
        System.out.println("Request: " + postReviewDTO.toString());

        Map<String, String> result = new HashMap<>();

        if(postReviewDTO.getReview() == null || postReviewDTO.getReview().isEmpty()) {
            result.put("fail", "내용을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }


        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            postReviewDTO.setUid(uid);
            postService.postReview(postReviewDTO);
            result.put("success", "성공");
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            result.put("fail", "실패");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/auth/review")
    public ResponseEntity editReview(HttpServletRequest request, @RequestBody PostReviewDTO postReviewDTO) {
        System.out.println("Request: " + postReviewDTO.toString());

        Map<String, String> result = new HashMap<>();
        if(postReviewDTO.getReview() == null || postReviewDTO.getReview().isEmpty()) {
            result.put("fail", "내용을 입력해주세요.");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            postReviewDTO.setUid(uid);
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

    @DeleteMapping("/auth/review")
    public ResponseEntity deleteReview(HttpServletRequest request, @RequestBody Map<String, Long> postDeleteReq) {
        System.out.println("Request: " + postDeleteReq.toString());

        Map<String, String> result = new HashMap<>();
        try{
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            postDeleteReq.put("uid", uid);
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

    @GetMapping("/auth/review")
    public ResponseEntity getReviewAll(HttpServletRequest request, @RequestParam(value = "postId") Long p_id,
                                        @RequestParam(value = "asc", defaultValue = "false") Boolean asc,
                                        @PageableDefault(size=10, page = 0) Pageable pageable){
        System.out.println("p_id: " + p_id);

        Map<String, String> result = new HashMap<>();
        if (p_id == null){
            result.put("fail", "잘못된 입력");
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        Long uid = Long.valueOf((String) request.getAttribute("uid"));

        try {
            return new ResponseEntity(postService.getReviewAll(uid, p_id, asc, pageable), HttpStatus.OK);
        } catch (Exception e) {
            result.put("fail", "댓글을 불러오는 것을 실패했습니다.");
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

    @GetMapping(value="/auth/like/list")
    public ResponseEntity getLikeList(HttpServletRequest request, @RequestParam(value = "postId") Long p_id,
                            @PageableDefault(size=10, page=0) Pageable pageable){
        Map<String, String> data = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(postService.getLikeList(uid, p_id, pageable), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/auth")
    public ResponseEntity readCustomCocktail(HttpServletRequest request, @RequestParam(value="postId") Long p_id){
        Map<String, String> data = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(postService.readCustomCocktail(p_id, uid), HttpStatus.OK);
        } catch (NoSuchElementException e){
            System.out.println(e);
            data.put("fail", "찾을 수 없음");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        }catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "잘못된 인풋");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/auth/feed")
    public ResponseEntity getNewsFeed(HttpServletRequest request, @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, String> result = new HashMap<>();

        try {
            Long me = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(postService.getNewsFeed(me, pageable), HttpStatus.OK);
        } catch (Exception e) {
            result.put("fail", "피드를 불러오는 것을 실패했습니다.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/material")
    public ResponseEntity getMaterialList(@RequestParam(value = "keyword") String keyword,
                                          @PageableDefault(size=10, page = 0) Pageable pageable){
        Map<String, String> result = new HashMap<>();

        try{
            return new ResponseEntity(searchService.searchMaterialList(keyword, pageable), HttpStatus.OK);
        }catch (Exception e){
            result.put("fail", "재료 검색을 실패했습니다.");
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
