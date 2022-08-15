package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.member.MemberVO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.*;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberProfilePageService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CocktailRepository cocktailRepository;
    private final CocktailCommentRepository cocktailCommentRepository;
    private final String s3Url;


    @Autowired
    public MemberProfilePageService(MemberRepository memberRepository,
                                    PostRepository postRepository,
                                    CocktailRepository cocktailRepository,
                                    CocktailCommentRepository cocktailCommentRepository,
                                    @Value("${cloud.aws.s3.bucket.path}") String s3Url) {

        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.cocktailRepository = cocktailRepository;
        this.cocktailCommentRepository = cocktailCommentRepository;
        this.s3Url = s3Url;
    }

    public Map<String, Object> readMemberProfile(Long myUid, Long uid){
        System.out.println(myUid + " " + uid);
        MemberVO data = memberRepository.findMemberProfile(myUid, uid).orElseThrow();

        System.out.println(data.getIsFollowed());
        Map<String, Object> res = new HashMap<>();
        res.put("uid", data.getUid().toString());
        res.put("email", data.getEmail());
        res.put("nickname", data.getNickname());
        res.put("profileImg", S3Util.s3urlFormatter(data.getProfileImg()));
        res.put("introduce", data.getIntroduce());
        res.put("followerCnt", data.getFollowerCnt().toString());
        res.put("followingCnt", data.getFollowingCnt().toString());
        res.put("postCnt", data.getPostCnt().toString());
        res.put("isPrivate", data.getIsPrivate());
        res.put("isFollowed", myUid.equals(uid) ? -1 : data.getIsFollowed());
        return res;
    }

    public List<Map<String, Object>> readMemberCocktailOrPost(Long userId, Long uid, Pageable pageable, String postType){
        List<Map<String, Object>> resArr = new ArrayList<>();
        Member member = memberRepository.findById(uid).orElseThrow();
        if(member.getIsPrivate().equals("1") && !member.getUid().equals(userId)){
            return resArr;
        }


        postRepository.findCocktailOrPostInProfilePageByUid(userId, uid, postType,pageable).forEach(data -> {
            Map<String, Object> res = new HashMap<>();
            res.put("nickname", data.getNickname());
            res.put("profileImg", S3Util.s3urlFormatter(data.getProfileImg()));
            res.put("createTime", data.getCreateTime().toString());
            res.put("postId", data.getPostId().toString());
            res.put("postImg", S3Util.s3urlFormatter(data.getPostImg()));
            res.put("description", data.getDescription());
            res.put("likes", data.getLikes().toString());
            res.put("iLike", data.getIlike().toString());
            res.put("title", data.getTitle());
            res.put("ingredients", data.getIngredients());
            if(postType == "cocktail"){
                res.put("baseCocktail", data.getBaseCocktail());
            }
            resArr.add(res);
        });


        return resArr;
    }

    public List<Map<String, String>> readMemberBookmark(Long uid, Pageable pageable){
        List<Map<String, String>> resArr = new ArrayList<>();

        cocktailRepository.findBookmarkInProfilePageByUid(uid, pageable).orElseThrow().forEach(data -> {
            Map<String, String> res = new HashMap<>();
            res.put("cocktailId", data.getId());
            res.put("nameKR", data.getNameKR());
            res.put("img", data.getImg());

            resArr.add(res);
        });

        return resArr;
    }

    public List<Map<String, String>> readMemberCocktailComment(Long userId, Long uid, Pageable pageable){
        List<Map<String, String>> resArr = new ArrayList<>();
        Member member = memberRepository.findById(uid).orElseThrow();
        if(member.getIsPrivate().equals("1") && !member.getUid().equals(userId)){
            return resArr;
        }

        cocktailCommentRepository.findCocktailCommentsInProfilePageByUid(uid, pageable).orElseThrow().forEach(data -> {
            Map<String, String> res = new HashMap<>();
            res.put("cocktailId", data.getId());
            res.put("nameKR", data.getNameKR());
            res.put("comment", data.getComment());

            resArr.add(res);
        });

        return resArr;
    }

    public Integer switchPublicToPrivate(Long uid){
        try {
            Member m = memberRepository.findById(uid).orElseThrow();
            Integer isPrivate = m.getIsPrivate() ^ 1;
            m.setIsPrivate(isPrivate);
            memberRepository.flush();

            return isPrivate;
        } catch(Exception e){
            return -1;
        }
    }
}
