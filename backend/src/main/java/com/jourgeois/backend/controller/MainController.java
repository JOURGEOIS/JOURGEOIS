package com.jourgeois.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // CORS 설정, 배포 시에 변경 필요
public class MainController {

    @RequestMapping(value="/")
    public String mainControl(){
        System.out.println("Main Controller");
        return "main";
    }
}