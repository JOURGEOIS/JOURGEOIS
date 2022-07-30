package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.CocktailService;
import com.jourgeois.backend.service.RedisService;
import com.jourgeois.backend.service.SearchHistoryService;
import com.jourgeois.backend.service.SearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class SearchController {

    public final CocktailService cocktailService;
    public final SearchService searchService;

    private final RedisService redisService;

    private final SearchHistoryService searchHistoryService;

    SearchController(CocktailService cocktailService, SearchService searchService, RedisService redisService, SearchHistoryService searchHistoryService){
        this.cocktailService = cocktailService;
        this.searchService = searchService;
        this.redisService = redisService;
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping(value="/cocktail")
    public ResponseEntity searchByKeyword(@RequestHeader(value = "email") String email, @RequestParam(value = "keyword") String keyword,
                                          @PageableDefault(size=10, sort="name", direction = Sort.Direction.ASC) Pageable pageable) {
        System.out.println(keyword);
        if (keyword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        try {
            if (!email.equals("null") && !email.isEmpty()) {
                this.redisService.setRecentKeyword(email, keyword);
            }
            this.searchHistoryService.writeSearchHistory(keyword);
        } catch (Exception e) {
            System.out.println("검색 로그 기록 실패");
        }
        return new ResponseEntity<>(searchService.searchByCocktail(keyword, pageable), HttpStatus.CREATED);
    }

    @GetMapping(value="/users")
    public ResponseEntity searchByUsers(@RequestParam(value = "keyword") String keyword,
                              @PageableDefault(size=10, sort="name", direction = Sort.Direction.ASC) Pageable pageable){
        System.out.println(keyword);
        if (keyword.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(null);
        return new ResponseEntity(searchService.searchByMember(keyword, pageable), HttpStatus.CREATED);
    }

    @GetMapping(value = "/search")
    public ResponseEntity keywordList(@RequestParam(value = "keyword") String keyword) {
        if (keyword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return new ResponseEntity(searchService.searchKeywords(keyword), HttpStatus.CREATED);
    }

    @GetMapping({"recentkeyword"})
    public ResponseEntity recentKeywordList(@RequestHeader("email") String email) {
        if (email.equals("null") || email.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(null);
        try {
            return new ResponseEntity(this.redisService.getRecentKeyword(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("데이터를 불러오는데 실패함", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"hotkeyword"})
    public ResponseEntity hotKeywordList() {
        try {
            return new ResponseEntity(this.redisService.getHotKeywords("cur"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("데이터를 불러오는데 실패함", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
