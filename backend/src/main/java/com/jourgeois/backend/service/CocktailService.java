package com.jourgeois.backend.service;


import com.jourgeois.backend.api.dto.*;
import com.jourgeois.backend.domain.*;
import com.jourgeois.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final MaterialRepository materialRepository;
    private final CocktailCommentRepository cocktailCommentRepository;
    private final MemberRepository memberRepository;
    private final CocktailBookmarkRepository cocktailBookmarkRepository;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository, CocktailCommentRepository cocktailCommentRepository,
                    MemberRepository memberRepository, CocktailBookmarkRepository cocktailBookmarkRepository){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
        this.cocktailCommentRepository = cocktailCommentRepository;
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

            CocktailComment cocktailReviews = new CocktailComment(m, c, cDTO.getComment());
            cocktailCommentRepository.save(cocktailReviews);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<CocktailCommentDTO> readComment(Long cocktailId, Pageable pageable, String criteria) {
        try {
            if(criteria == "date"){
                criteria = "modified_date";
            }

            List<CocktailCommentVO> ccVOs = cocktailCommentRepository.findCommentsByCocktailId(cocktailId, pageable, criteria).orElseThrow(() -> new Exception("cocktailService.readComment"));
            List<CocktailCommentDTO> ccDTOs = new ArrayList<>();

            for (CocktailCommentVO ccVO : ccVOs) {
                CocktailCommentDTO ccDTO = CocktailCommentDTO.builder().commentId(ccVO.getCommentId()).nickname(ccVO.getNickname())
                        .profileImg(ccVO.getProfileImg()).createdDate(ccVO.getCreatedDate()).modifiedDate(ccVO.getModifiedDate())
                        .comment(ccVO.getComment()).cocktailId(ccVO.getCocktailId()).likes(ccVO.getLikes()).userId(ccVO.getUserId()).build();

                ccDTOs.add(ccDTO);
            }
            return ccDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateComment(CocktailCommentDTO cocktailCommentDTO) {
        try {
            Long commentId = cocktailCommentDTO.getCommentId();
            String comment = cocktailCommentDTO.getComment();

            CocktailComment findComment = cocktailCommentRepository.findCocktailCommentByCommentId(commentId).orElseThrow();
            findComment.setComment(comment);
            cocktailCommentRepository.save(findComment);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean deleteComment(Long userId, Long commentId) {
        try {
            CocktailComment cc = cocktailCommentRepository.findByUidAndCommentId(userId, commentId)
                                .orElseThrow(() -> new Exception("CocktailService.deleteComment"));
            cocktailCommentRepository.delete(cc);
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
