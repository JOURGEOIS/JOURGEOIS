package com.jourgeois.backend.service;


import com.jourgeois.backend.api.dto.cocktail.*;
import com.jourgeois.backend.api.dto.member.FollowerDTO;
import com.jourgeois.backend.domain.cocktail.*;
import com.jourgeois.backend.domain.member.FollowPK;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final MaterialRepository materialRepository;
    private final CocktailCommentRepository cocktailCommentRepository;
    private final MemberRepository memberRepository;
    private final CocktailBookmarkRepository cocktailBookmarkRepository;
    private final FollowRepository followRepository;
    private final String s3Url;

    @Autowired
    CocktailService(CocktailRepository cocktailRepository, MaterialRepository materialRepository, CocktailCommentRepository cocktailCommentRepository,
                    MemberRepository memberRepository, CocktailBookmarkRepository cocktailBookmarkRepository, FollowRepository followRepository,
                    @Value("${cloud.aws.s3.bucket.path}") String s3Url){
        this.cocktailRepository = cocktailRepository;
        this.materialRepository = materialRepository;
        this.cocktailCommentRepository = cocktailCommentRepository;
        this.memberRepository = memberRepository;
        this.cocktailBookmarkRepository = cocktailBookmarkRepository;
        this.followRepository = followRepository;
        this.s3Url = s3Url;
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

    public List<CocktailCommentDTO> readComment(Long cocktailId, Pageable pageable) {
        try {
            List<CocktailCommentVO> ccVOs = cocktailCommentRepository.findCommentsByCocktailId(cocktailId, pageable).orElseThrow(() -> new Exception("cocktailService.readComment"));
            List<CocktailCommentDTO> ccDTOs = new ArrayList<>();

            for (CocktailCommentVO ccVO : ccVOs) {
                CocktailCommentDTO ccDTO = CocktailCommentDTO.builder().commentId(ccVO.getCommentId()).nickname(ccVO.getNickname())
                        .profileImg(ccVO.getProfileImg()).createdDate(ccVO.getCreatedDate()).modifiedDate(ccVO.getModifiedDate())
                        .comment(ccVO.getComment()).cocktailId(ccVO.getCocktailId()).userId(ccVO.getUserId()).build();

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
    public boolean pushBookmark(Map<String, Long> cocktailBookmark){
        Long m_id = cocktailBookmark.get("uid");
        Long c_id = cocktailBookmark.get("c_id");
        CocktailBookmarkId key = new CocktailBookmarkId(m_id, c_id);

        Member member = new Member();
        member.setUid(m_id);
        Cocktail cocktail = new Cocktail();
        cocktail.setId(c_id);

        cocktailBookmarkRepository.findById(key).ifPresentOrElse(data -> {
            cocktailBookmarkRepository.deleteById(key);},
                ()->{cocktailBookmarkRepository.save(new CocktailBookmark(member, cocktail));});

        return checkUserBookmark(key);
    }

    public boolean checkUserBookmark(CocktailBookmarkId key){
        return cocktailBookmarkRepository.findById(key).isPresent();
    }

    public boolean checkCocktailUid(Long uid){
        return cocktailRepository.findById(uid).isPresent();
    }

    public Long countCocktailBookmark(Cocktail cocktail_id){
        return cocktailBookmarkRepository.countByCocktailId(cocktail_id);
    }

    // uid와 칵테일 id로
    public List<FollowerDTO> getBookmarkList(Long uid, Long c_id, Pageable pageable){
        List<FollowerDTO> followersResponse = new ArrayList<>();

        // c_id를 북마크 한 사람들 목록 가져오기
        cocktailBookmarkRepository.findByCocktailId(new Cocktail(c_id), pageable).forEach(data -> {
            Member member = memberRepository.findById(data.getMemberId().getUid()).orElseThrow();
            FollowPK key = new FollowPK(uid, member.getUid());

            Integer status = followRepository.findById(key).isPresent() ? 1 : 0;
            if(uid.equals(member.getUid())) {
                status = -1;
            }

            followersResponse.add(FollowerDTO.builder()
                    .isFollowed(status)
                    .nickname(member.getNickname())
                    .uid(member.getUid())
                    .profileImg(s3Url+member.getProfileImg())
                    .build());
        });
        return followersResponse;
    }
}
