package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.CocktailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cocktail")
public class CocktailController {
    public final CocktailService cocktailService;

    CocktailController(CocktailService cocktailService){
        this.cocktailService = cocktailService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity keywordList(HttpServletRequest request, @RequestParam String keyword){
        System.out.println(keyword);
        if(keyword.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return new ResponseEntity(cocktailService.searchKeywords(keyword), HttpStatus.CREATED);
    }
}
