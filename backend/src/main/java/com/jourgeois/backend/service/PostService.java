package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.PostDTO;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.domain.Post;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.PostRepository;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final S3Util s3Util;
    private final String s3Url;

    @Autowired
    public PostService(PostRepository postRepository,
                       MemberRepository memberRepository,
                       S3Util s3Util,
                       @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
    }

    public void postPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long writerId = postDTO.getWriter();

        Member member = memberRepository.findById(writerId)
                            .orElseThrow(()->new NoSuchElementException("유저를 찾을 수 없습니다."));

        Post post = new Post();
        post.setDescription(postDTO.getDescription());
        post.setMember(member);
        // post 이미지 업로드
        String uploadURL = s3Url + s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);
        post.setImg(uploadURL);

        // post 저장
        postRepository.save(post);
    }
}
