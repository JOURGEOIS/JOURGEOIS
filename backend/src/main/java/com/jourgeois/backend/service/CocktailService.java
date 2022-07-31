package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.CocktailDTO;
import com.jourgeois.backend.api.dto.CocktailVO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
    }

    // 칵테일 저장 메소드
    public boolean createCocktail(Cocktail cocktail){
        try {
            cocktailRepository.save(cocktail);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public CocktailDTO readCocktail(Long id) throws Exception {
        CocktailVO cocktailInfo = cocktailRepository.findCocktailById(id).orElseThrow(() -> new Exception("CocktailService.readCocktail.cInfo"));
        String cupInfo = cocktailRepository.findCocktailCupById(id).orElse("adassd");
        ArrayList<String> materialsInfo = cocktailRepository.findAllMaterialsByCocktailId(id).orElseThrow(() -> new Exception("CocktailService.readCocktail.materialsInfo"));

        System.out.println(cocktailInfo.getId());
        System.out.println(cocktailInfo.getName());
        System.out.println(cocktailInfo.getRecipe());
        System.out.println(cocktailInfo.getTag());

        CocktailDTO cocktailDTO = CocktailDTO.builder().id(cocktailInfo.getId()).name(cocktailInfo.getName()).nameKR(cocktailInfo.getNameKR())
                .alcohol(cocktailInfo.getAlcohol()).cupName(cupInfo).tag(cocktailInfo.getTag()).baseLiquor(cocktailInfo.getBaseLiquor())
                .category(cocktailInfo.getCategory()).recipe(cocktailInfo.getRecipe()).img(cocktailInfo.getImg()).materials(materialsInfo).build();

        return cocktailDTO;
    }


    public boolean updateCocktail(Cocktail cocktail) throws Exception {
//        Cocktail findCocktail = cocktailRepository.findById(cocktail.getId()).orElseThrow(() -> new Exception("CocktailService.updateCocktail.findCocktail"));
//        cocktailRepository.updateCocktail(cocktail); //.orElseThrow(() -> new Exception("CocktailService.updateCocktail.update"));
        return true;
    }

    public boolean deleteCocktail(Long id) {
        try{
            cocktailRepository.deleteById(id);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertMaterial(Material material){
        try {
            //materialRepository.save(material);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
