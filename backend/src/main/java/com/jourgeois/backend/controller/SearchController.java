package com.jourgeois.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lookup")
public class SearchController {

    @GetMapping()
    public @ResponseBody String searchByKeyword(@RequestParam(value = "keyword") String keyword) {
        System.out.println(keyword);
        return null;
    }
}
