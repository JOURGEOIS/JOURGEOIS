package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.CocktailDTO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.service.CocktailService;
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

    @Autowired
    CocktailController(CocktailService cocktailService){
        this.cocktailService = cocktailService;
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
    public ResponseEntity<?> insertMaterial(@RequestBody Material material) {
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
}
