package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.CocktailBookmarkDTO;
import com.jourgeois.backend.api.dto.CocktailDTO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CocktailBookmark;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.service.CocktailService;
import com.jourgeois.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/cocktail")
public class CocktailController {
    private final CocktailService cocktailService;
    private final MemberService memberService;
    @Autowired
    CocktailController(CocktailService cocktailService,
                       MemberService memberService){
        this.cocktailService = cocktailService;
        this.memberService = memberService;
    }

    @GetMapping(value = "/cocktail")
    public ResponseEntity readCocktail(@RequestParam(value = "id") Long id) {
        try {
            System.out.println(id+" 을 조회하셨습니다.");
            CocktailDTO cocktail = cocktailService.readCocktail(id);

            return ResponseEntity.ok().body(cocktail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("result", "none"));
        }

        //
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
    public ResponseEntity<?> updateCocktail(@RequestBody Cocktail cocktail) {
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

    // 원본 칵테일 북마크 (token 필요) => uid로 전체 바꿀 때 바꾼 후 적용 필요
    @PostMapping(value = "/bookmark")
    public ResponseEntity pushCocktailBookmark(@RequestBody Map<String, Long> bookmark){
        if(memberService.checkUserUid(bookmark.get("m_id")) && cocktailService.checkCocktailUid(bookmark.get("c_id"))){
            CocktailBookmarkDTO df = CocktailBookmarkDTO.builder()
                    .member(new Member(bookmark.get("m_id")))
                    .cocktail(new Cocktail(bookmark.get("c_id"))).build();
            Map<String, Long> data = new HashMap<>();
            if(cocktailService.pushBookmark(df)){
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
}
