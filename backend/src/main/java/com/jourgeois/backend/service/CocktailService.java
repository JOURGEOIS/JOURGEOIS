package com.jourgeois.backend.service;


import com.jourgeois.backend.api.dto.*;
import com.jourgeois.backend.domain.*;
import com.jourgeois.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final MaterialRepository materialRepository;
    private final CocktailReviewRepository cocktailReviewRepository;
    private final MemberRepository memberRepository;
    private final CocktailBookmarkRepository cocktailBookmarkRepository;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository, CocktailReviewRepository cocktailReviewRepository,
                    MemberRepository memberRepository, CocktailBookmarkRepository cocktailBookmarkRepository){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
        this.cocktailReviewRepository = cocktailReviewRepository;
        this.memberRepository = memberRepository;
        this.cocktailBookmarkRepository = cocktailBookmarkRepository;
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

    public boolean createComment(CocktailCommentDTO cDTO) {
        try{
            Member m = memberRepository.findById(cDTO.getUserId()).orElseThrow();
            Cocktail c = cocktailRepository.findById(cDTO.getCocktailId()).orElseThrow();

            CocktailReviews cocktailReviews = new CocktailReviews(m, c, cDTO.getReview());
            cocktailReviewRepository.save(cocktailReviews);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<CocktailCommentDTO> readComment(Long cocktailId) {
        try {
            List<CocktailCommentVO> ccVOs = cocktailReviewRepository.findBycId(cocktailId).orElseThrow(() -> new Exception("cocktailService.readComment"));
            List<CocktailCommentDTO> ccDTOs = new ArrayList<>();

            for (CocktailCommentVO ccVO : ccVOs) {
                CocktailCommentDTO ccDTO = CocktailCommentDTO.builder().id(ccVO.getId()).createdDate(ccVO.getCreatedDate())
                        .modifiedDate(ccVO.getModifiedDate()).review(ccVO.getReview()).cocktailId(ccVO.getCocktailId()).userId(ccVO.getUserId()).build();
                System.out.println(ccDTO.toString());
                ccDTOs.add(ccDTO);
            }
            System.out.println("ccDTO size : " + ccDTOs.size());
            return ccDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateComment(CocktailCommentDTO cocktailCommentDTO) {
        try {
//            cocktailReviewRepository.save(cocktailCommentDTO);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
    }

    public boolean checkCocktailUid(Long uid){
        return cocktailRepository.findById(uid).isPresent();
    }

    public Long countCocktailBookmark(Cocktail cocktail_id){
        return cocktailBookmarkRepository.countByCocktail(cocktail_id);
    }
}
