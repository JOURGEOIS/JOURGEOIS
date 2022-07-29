package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.CocktailService;
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

    SearchController(CocktailService cocktailService, SearchService searchService){
        this.cocktailService = cocktailService;
        this.searchService = searchService;
    }

    @GetMapping(value="/cocktail")
    public ResponseEntity searchByKeyword(@RequestParam(value = "keyword") String keyword,
                                          @PageableDefault(size=10, sort="name", direction = Sort.Direction.ASC) Pageable pageable) {
        System.out.println(keyword);
        if (keyword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return new ResponseEntity<>(searchService.searchByCocktail(keyword, pageable), HttpStatus.CREATED);
    }

    @GetMapping(value="/users")
    public ResponseEntity searchByUsers(@RequestParam(value = "keyword") String keyword,
                              @PageableDefault(size=10, sort="name", direction = Sort.Direction.ASC) Pageable pageable){
        System.out.println(keyword);
        if (keyword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return new ResponseEntity(searchService.searchByMember(keyword, pageable), HttpStatus.CREATED);
    }

    @GetMapping(value = "/search")
    public ResponseEntity keywordList(@RequestParam(value = "keyword") String keyword) {
        if (keyword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return new ResponseEntity(searchService.searchKeywords(keyword), HttpStatus.CREATED);
    }
}
