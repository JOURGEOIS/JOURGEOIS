package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.post.CocktailAwardsDTO;
import com.jourgeois.backend.api.dto.post.CocktailAwardsVO;
import com.jourgeois.backend.api.dto.post.PostDTO;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.*;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.PostBookmarkRepository;
import com.jourgeois.backend.repository.PostRepository;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CocktailAwardsService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final S3Util s3Util;
    private final String s3Url;

    public CocktailAwardsService(PostRepository postRepository,
                                 MemberRepository memberRepository,
                                 PostBookmarkRepository postBookmarkRepository,
                                 S3Util s3Util,
                                 @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postBookmarkRepository = postBookmarkRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
    }

    @Transactional
    public Long postAwards(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long writerId = postDTO.getUid();

        Member member = memberRepository.findById(writerId)
                .orElseThrow(()->new NoSuchElementException("????????? ?????? ??? ????????????."));

        // ?????? ??????
        if(postDTO.getTitle() == null || postDTO.getTitle().trim().isEmpty()) {
            throw  new IllegalArgumentException("?????? ?????? ??????");
        }

        CocktailAwards post = new CocktailAwards();
        post.setDescription(postDTO.getDescription());
        post.setMember(member);
        // post ????????? ?????????
        String uploadURL = s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);

        post.setImg(uploadURL);
        post.setTitle(postDTO.getTitle());

        // post ??????
        return postRepository.save(post).getId();
    }

    public Boolean postCheck(Long uid){
        return postRepository.findByCocktailAwards(uid, "cocktail_awards")==0;
    }

    public List<CocktailAwardsDTO> getCocktailAwardsPostList(Long uid, Pageable pageable){
        List<CocktailAwardsDTO> list = new ArrayList<>();
        postRepository.getCocktailAwardsList(uid, pageable)
                .forEach(data-> {
                    list.add(CocktailAwardsDTO.builder()
                            .postId(data.getPostId())
                            .imgLink(s3Url + data.getImgLink())
                            .title(data.getTitle())
                            .like(data.getLike()).build());
                });
        return list;
    }

    public List<CocktailAwardsDTO> getCocktailAwardsVoteList(Pageable pageable){
        List<CocktailAwardsDTO> list = new ArrayList<>();
        postRepository.getCocktailAwardsVoteList(pageable)
                .forEach(data-> {
                    list.add(CocktailAwardsDTO.builder()
                            .postId(data.getPostId())
                            .imgLink(s3Url + data.getImgLink())
                            .title(data.getTitle())
                            .percentage(data.getPercentage()).build());
                });
        return list;
    }

    public CocktailAwardsDTO getCocktailAwardsPostInfo(Long memberId, Long postId){
        CocktailAwardsVO data = postRepository.getCocktailAwardsPostInfo(memberId, postId).get();
        CocktailAwardsDTO result =
                CocktailAwardsDTO.builder()
                        .postId(data.getPostId())
                        .imgLink(s3Url + data.getImgLink())
                        .title(data.getTitle())
                        .like(data.getLike())
                        .description(data.getDescription())
                        .percentage(data.getPercentage()).build();
        return result;
    }
}
