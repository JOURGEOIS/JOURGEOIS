package com.jourgeois.backend.service;

<<<<<<< HEAD
import com.jourgeois.backend.api.dto.CocktailCommentDTO;
import com.jourgeois.backend.api.dto.CocktailDTO;
import com.jourgeois.backend.api.dto.CocktailVO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CocktailReviews;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.domain.Member;
=======
import com.jourgeois.backend.api.dto.CocktailBookmarkDTO;
import com.jourgeois.backend.api.dto.CocktailDTO;
import com.jourgeois.backend.api.dto.CocktailVO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CocktailBookmark;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.repository.CocktailBookmarkRepository;
>>>>>>> 8fdaec2e5c60d20415aed47079ead4c732528e0a
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.CocktailReviewRepository;
import com.jourgeois.backend.repository.MaterialRepository;
import com.jourgeois.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final MaterialRepository materialRepository;
<<<<<<< HEAD
    private final CocktailReviewRepository cocktailReviewRepository;
    private final MemberRepository memberRepository;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository,
                    CocktailReviewRepository cocktailReviewRepository, MemberRepository memberRepository){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
        this.cocktailReviewRepository = cocktailReviewRepository;
        this.memberRepository = memberRepository;
=======
    private final CocktailBookmarkRepository cocktailBookmarkRepository;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository
                    ,CocktailBookmarkRepository cocktailBookmarkRepository
    ){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
        this.cocktailBookmarkRepository = cocktailBookmarkRepository;
>>>>>>> 8fdaec2e5c60d20415aed47079ead4c732528e0a
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

<<<<<<< HEAD
    public boolean createComment(CocktailCommentDTO cDTO) throws Exception {
        try{
            Member m = memberRepository.findById(cDTO.getId()).orElseThrow();
            Cocktail c = cocktailRepository.findById(cDTO.getCocktailId()).orElseThrow();

            CocktailReviews cocktailReviews = new CocktailReviews(0L, c,cDTO.getReview(),m);
            cocktailReviewRepository.save(cocktailReviews);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean readComment(CocktailCommentDTO cocktailCommentDTO) throws Exception {
        try{
//            cocktailReviewRepository.save(cocktailCommentDTO);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
=======
    @Transactional
    public boolean pushBookmark(CocktailBookmarkDTO cocktailBookmark){
        cocktailBookmarkRepository.findByMemberAndCocktail(cocktailBookmark.getMember(),
                cocktailBookmark.getCocktail()).ifPresentOrElse(data -> {
                    cocktailBookmarkRepository.deleteById(data.getId());},
            ()->{cocktailBookmarkRepository.save(new CocktailBookmark(cocktailBookmark.getMember(), cocktailBookmark.getCocktail()));});
        return checkUserBookmark(cocktailBookmark);
    }

    public boolean checkUserBookmark(CocktailBookmarkDTO cocktailBookmark){
        return cocktailBookmarkRepository.findByMemberAndCocktail(cocktailBookmark.getMember(),
                cocktailBookmark.getCocktail()).isPresent();
>>>>>>> 8fdaec2e5c60d20415aed47079ead4c732528e0a
    }

    public boolean checkCocktailUid(Long uid){
        return cocktailRepository.findById(uid).isPresent();
    }

    public Long countCocktailBookmark(Cocktail cocktail_id){
        return cocktailBookmarkRepository.countByCocktail(cocktail_id);
    }
}
