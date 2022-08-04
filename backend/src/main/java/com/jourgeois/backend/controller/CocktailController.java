package com.jourgeois.backend.controller;


import com.jourgeois.backend.api.dto.cocktail.CocktailCommentDTO;

import com.jourgeois.backend.api.dto.cocktail.CocktailDTO;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.cocktail.CocktailBookmarkId;
import com.jourgeois.backend.domain.cocktail.Material;
import com.jourgeois.backend.service.CocktailService;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cocktail")
public class CocktailController {
    private final CocktailService cocktailService;
    private final MemberService memberService;
    private final PostService postService;
    @Autowired
    CocktailController(CocktailService cocktailService,
                       MemberService memberService, PostService postService){
        this.cocktailService = cocktailService;
        this.memberService = memberService;
        this.postService = postService;
    }

    @GetMapping(value = "/cocktail")
    public ResponseEntity readCocktail(@RequestHeader(value = "uid", defaultValue = "0") Long uid, @RequestParam(value = "id") Long id) {
        try {
            CocktailDTO cocktail = cocktailService.readCocktail(id);
            cocktail.setCount(cocktailService.countCocktailBookmark(new Cocktail(id)));
            if (uid != 0) {
                CocktailBookmarkId key = new CocktailBookmarkId(uid, id);
                cocktail.setStatus(cocktailService.checkUserBookmark(key) ? 1L : 0L);
            }
            return ResponseEntity.ok().body(cocktail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("result", "none"));
        }

    }

    @PostMapping(value = "/cocktail")
    public ResponseEntity createCocktail(@RequestBody Cocktail cocktail) {
        try {
            boolean res = cocktailService.createCocktail(cocktail);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("result", "false"));
        }
    }

    @PutMapping(value = "/cocktail")
    public ResponseEntity<?> updateCocktail(@RequestParam(value = "id") Long id) {
            Cocktail cocktail = null;
//            (@RequestBody Cocktail cocktail) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            System.out.println();
            boolean res = cocktailService.updateCocktail(cocktail);
            data.put("success", res);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            data.put("success", false);
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
    }

    //수정중
    @Transactional
    @DeleteMapping(value = "/cocktail")
    public ResponseEntity<?> deleteCocktail(@RequestBody Map<String, Long> idMap) {
        Map<String, Boolean> data = new HashMap<>();
        Long id = idMap.get("id");
        try {
            boolean res = cocktailService.deleteCocktail(id);
            data.put("success", res);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            data.put("success", false);
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/material")
    public ResponseEntity insertMaterial(@RequestBody Material material) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            boolean res = cocktailService.insertMaterial(material);
            data.put("success", res);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            data.put("success", false);
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/comment")
    public ResponseEntity insertReview(@RequestBody CocktailCommentDTO cocktailCommentDTO) {
        // uId, cocktailId, review  정보 받음
        System.out.println(cocktailCommentDTO.toString());
        try {
            boolean res = cocktailService.createComment(cocktailCommentDTO);
            return ResponseEntity.ok().body(Map.of("success", "true"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("success", "false"));
        }
    }

    @GetMapping(value = "/comment")
    public ResponseEntity selectReview(@RequestParam(value = "cocktailId") Long cocktailId,
                                       @PageableDefault(size=10, page = 0) Pageable pageable) {
        // cocktailId, page, page size 정보 받음
        // criteria -> modifiedDate (등록/수정 날짜 기준), likes(좋아요 기준)
        try {
            List<CocktailCommentDTO> cc = cocktailService.readComment(cocktailId, pageable);
            return ResponseEntity.ok().body(cc);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("result", "none"));
        }
    }

    @PutMapping(value = "/comment")
    public ResponseEntity updateReview(@RequestBody CocktailCommentDTO cocktailCommentDTO) {
        // commentId, comment 받음
        try {
            cocktailService.updateComment(cocktailCommentDTO);
            return ResponseEntity.ok().body(Map.of("success", "true"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("success", "false"));
        }
    }

    @DeleteMapping(value = "/comment")
    public ResponseEntity deleteReview(@RequestBody CocktailCommentDTO cDTO) {
        try {
            boolean res = cocktailService.deleteComment(cDTO.getUserId(), cDTO.getCommentId());
            return ResponseEntity.ok().body(Map.of("success", res));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("success", "false"));
        }
    }

    // 원본 칵테일 북마크 (token 필요) => uid로 전체 바꿀 때 바꾼 후 적용 필요
    @PostMapping(value = "/bookmark")
    public ResponseEntity pushCocktailBookmark(@RequestBody Map<String, Long> bookmark){
        Map<String, Long> data = new HashMap<>();
        if(memberService.checkUserUid(bookmark.get("uid")) && cocktailService.checkCocktailUid(bookmark.get("c_id"))){
            if(cocktailService.pushBookmark(bookmark)){
                data.put("status", 1L);
            }else{
                data.put("status", 0L);
            }
            data.put("count", cocktailService.countCocktailBookmark(new Cocktail(bookmark.get("c_id"))));
            return new ResponseEntity<>(data, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("fail", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // uid와 칵테일 id로
    @GetMapping(value="/bookmarklist")
    public ResponseEntity getBookmarkList(@RequestParam(value = "uid") Long uid, @RequestParam(value = "c_id") Long c_id,
                                @PageableDefault(size=10, page=0) Pageable pageable){
        Map<String, String> data = new HashMap<>();
        try {
            return new ResponseEntity(cocktailService.getBookmarkList(uid, c_id, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "잘못된 인풋");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 원본칵테일의 커스텀칵테일 탭 (리스트반환)
    @GetMapping
    public ResponseEntity readCustomCocktailList(@RequestParam Long id, @RequestParam int type,
                                                 @PageableDefault(size=10, page = 0) Pageable pageable){
        try{
            return  new ResponseEntity(postService.readCumstomCoctailList(id, type, pageable), HttpStatus.CREATED);
        }catch (NumberFormatException e) {
            System.out.println(e);
            return new ResponseEntity("잘못된 인풋", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("리스트를 불러오지 못했습니다.", HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
