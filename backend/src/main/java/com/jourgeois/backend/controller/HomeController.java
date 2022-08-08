package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.HomeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    public final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
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
}
