package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.member.MemberVO;
import com.jourgeois.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public MemberDTO readMemberProfile(Long uid){
        MemberVO memberVO = memberRepository.findMemberProfile(uid).orElseThrow();

        MemberDTO m = MemberDTO.builder().uid(memberVO.getUid()).email(memberVO.getEmail()).nickname(memberVO.getNickname())
                .profileImg(memberVO.getProfileImg()).introduce(memberVO.getIntroduce()).followerCnt(memberVO.getFollowerCnt())
                .followingCnt(memberVO.getFollowingCnt()).postCnt(memberVO.getPostCnt()).build();

        return m;
    }

    public List<Map<String, String>> readMemberCocktailOrPost(Long uid, String postType){
        List<Map<String, String>> resArr = new ArrayList<>();

        postRepository.findCocktailOrPostInProfilePageByUid(uid, postType).orElseThrow().forEach(data -> {
            Map<String, String> res = new HashMap<>();
            res.put("nickname", data.getNickname());
            res.put("profileImg", data.getProfileImg());
            res.put("createTime", data.getCreateTime());
            res.put("postImg", data.getPostImg());
            res.put("description", data.getDescription());

            resArr.add(res);
        });

        return resArr;
    }

    public List<Map<String, String>> readMemberBookmark(Long uid){
        List<Map<String, String>> resArr = new ArrayList<>();

        cocktailRepository.findBookmarkInProfilePageByUid(uid).orElseThrow().forEach(data -> {
            Map<String, String> res = new HashMap<>();
            res.put("cocktailId", data.getId());
            res.put("nameKR", data.getNameKR());
            res.put("img", data.getImg());
            res.put("category", data.getCategory());
            res.put("tag", data.getTag());

            resArr.add(res);
        });

        return resArr;
    }

    public List<Map<String, String>> readMemberCocktailComment(Long uid){
        List<Map<String, String>> resArr = new ArrayList<>();

        cocktailCommentRepository.findCocktailCommentsInProfilePageByUid(uid).orElseThrow().forEach(data -> {
            Map<String, String> res = new HashMap<>();
            res.put("cocktailId", data.getId());
            res.put("nameKR", data.getNameKR());
            res.put("img", data.getImg());
            res.put("category", data.getCategory());
            res.put("tag", data.getTag());
            res.put("comment", data.getComment());

            resArr.add(res);
        });

        return resArr;
    }
}
