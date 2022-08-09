package com.jourgeois.backend.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
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
                .orElseThrow(()->new NoSuchElementException("유저를 찾을 수 없습니다."));

        // 공백 처리
        if(postDTO.getTitle() == null || postDTO.getTitle().trim().isEmpty()) {
            throw  new IllegalArgumentException("필수 정보 누락");
        }

        CocktailAwards post = new CocktailAwards();
        post.setDescription(postDTO.getDescription());
        post.setMember(member);
        // post 이미지 업로드
        String uploadURL = s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);

        post.setImg(uploadURL);
        post.setTitle(postDTO.getTitle());

        // post 저장
        return postRepository.save(post).getId();
    }

    public Boolean postCheck(Long uid){
        return postRepository.findByCocktailAwards(uid, "cocktail_awards")==0;
    }


}
