package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.HomeService;
import com.jourgeois.backend.util.TagType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    public final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(value="/liquor5")
    public ResponseEntity recommender5Liquor(@RequestHeader(value = "uid", defaultValue = "-1") Long uid){
        try{
            return new ResponseEntity(homeService.recommender5Liquor(uid), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/liquor")
    public ResponseEntity recommenderLiquor(@RequestHeader(value = "uid", defaultValue = "-1") Long uid,
                                            @PageableDefault(size=10, page=0) Pageable pageable){
        try{
            return new ResponseEntity(homeService.recommenderLiquor(uid, pageable), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/custom/latest5")
    public ResponseEntity latestTop5CustomCocktail() {
        try{
            return new ResponseEntity(homeService.getLatestTop5CustomCocktail(), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/custom/latest")
    public ResponseEntity latestCustomCocktailAll(@PageableDefault(size=10, page = 0) Pageable pageable) {
        try{
            return new ResponseEntity(homeService.getLatestCustomCocktail(pageable), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cocktail/hot5")
    public ResponseEntity hotTop5Cocktail() {
        try{
            return new ResponseEntity(homeService.getHotTop5Cocktail(), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cocktail/hot")
    public ResponseEntity hotCocktailAll(@PageableDefault(size=10, page = 0) Pageable pageable) {
        try{
            return new ResponseEntity(homeService.getHotCocktail(pageable), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/custom/weekly5")
    public ResponseEntity weeklyHot5CustomCocktail() {
        try{
            return new ResponseEntity(homeService.getWeeklyHot5CustomCocktail(), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/custom/weekly")
    public ResponseEntity weeklyHotCustomCocktail(@PageableDefault(size=10, page = 0) Pageable pageable) {
        try{
            return new ResponseEntity(homeService.getWeeklyHotCustomCocktail(pageable), HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/clip")
    public ResponseEntity getRandomClip() {
        try{
            Map<String, String> result = new HashMap<>();
            result.put("url", homeService.getRandomClip());
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/tag5")
    public ResponseEntity Tag5Cocktail(@RequestParam("tag") String tagType) {
        try{
            TagType tag = TagType.valueOf(tagType);
            return new ResponseEntity(homeService.getTag5Cocktail(tag), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/tag")
    public ResponseEntity TagCocktail(@RequestParam("tag") String tagType, @PageableDefault(size=10, page = 0) Pageable pageable) {
        try{
            TagType tag = TagType.valueOf(tagType);
            return new ResponseEntity(homeService.getTagCocktail(tag, pageable), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
