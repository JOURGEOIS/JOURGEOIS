package com.jourgeois.backend.service;

import com.amazonaws.SdkClientException;
import com.jourgeois.backend.api.dto.PostDTO;
import com.jourgeois.backend.api.dto.PostReviewDTO;
import com.jourgeois.backend.domain.*;
import com.jourgeois.backend.repository.*;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CustomCocktailToCocktailRepository customCocktailToCocktailRepository;
    private final CocktailRepository cocktailRepository;

    private final PostReviewRepository postReviewRepository;
    private final S3Util s3Util;
    private final String s3Url;

    @Autowired
    public PostService(PostRepository postRepository,
                       MemberRepository memberRepository,
                       CustomCocktailToCocktailRepository customCocktailToCocktailRepository, CocktailRepository cocktailRepository, PostReviewRepository postReviewRepository, S3Util s3Util,
                       @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.customCocktailToCocktailRepository = customCocktailToCocktailRepository;
        this.cocktailRepository = cocktailRepository;
        this.postReviewRepository = postReviewRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
    }

    @Transactional
    public void postPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long writerId = postDTO.getUid();

        Member member = memberRepository.findById(writerId)
                            .orElseThrow(()->new NoSuchElementException("유저를 찾을 수 없습니다."));
        // post 이미지 업로드
        String uploadURL = s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);
        if(postDTO.getTitle()==null || postDTO.getTitle().isEmpty()){
            Post post = new Post();
            post.setDescription(postDTO.getDescription());
            post.setMember(member);
            post.setImg(uploadURL);

            // post 저장
            postRepository.save(post);
        }else{
            CustomCocktail cc = new CustomCocktail();
            cc.setTitle(postDTO.getTitle());
            cc.setDescription(postDTO.getDescription());
            cc.setMember(member);
            cc.setIngredients(postDTO.getIngredients());
            cc.setRecipe(postDTO.getRecipe());
            cc.setImg(uploadURL);
            // post 저장
            CustomCocktail cocktail = postRepository.save(cc);

            // 베이스가 있는 커스텀 칵테일이라면
            if(postDTO.getBaseCocktail()!=null){
                Cocktail ori = cocktailRepository.findById(postDTO.getBaseCocktail()).get();
                customCocktailToCocktailRepository.findByCustomCocktailAndCocktail(cocktail, ori)
                                .ifPresentOrElse(data -> {new Exception("BaseCocktail  save Error");},
                                        ()->{customCocktailToCocktailRepository.save(new CustomCocktailToCocktail(cocktail, ori));});
            }
        }

    }

    public String postImageLocalUpload(PostDTO postDTO) throws IOException {
        Long uid = postDTO.getUid();
        String url = s3Util.localUpload(postDTO.getImg(), uid, ImgType.POST);
        return url;
    }

    @Transactional
    public void editPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long postId = postDTO.getPostId();
        // post 이미지 업로드
        String uploadURL = s3Util.upload(postDTO.getImg(), postDTO.getUid(), ImgType.POST);

        // 커스텀 칵테일 제목이 Empty라면 일반 게시물이라는 의미
        // postDTO.getTitle().isEmpty() 가끔씩 됨... 이유를 알고 싶다.
        if(postDTO.getTitle()==null || postDTO.getTitle().isEmpty()){
            Post targetPost = postRepository.findById(postId).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
            targetPost.setDescription(postDTO.getDescription());

            targetPost.setImg(uploadURL);
            s3Util.deleteFile(targetPost.getImg());
            // post 저장
            postRepository.save(targetPost);
        }else{
            CustomCocktail targetCustomCocktail = (CustomCocktail) postRepository.findById(postId).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
            targetCustomCocktail.setDescription(postDTO.getDescription());
            targetCustomCocktail.setImg(uploadURL);
            s3Util.deleteFile(targetCustomCocktail.getImg());
            targetCustomCocktail.setTitle(postDTO.getTitle());
            targetCustomCocktail.setRecipe(postDTO.getRecipe());
            targetCustomCocktail.setIngredients(postDTO.getIngredients());
            // post 저장
            postRepository.save(targetCustomCocktail);
        }

    }

    @Transactional
    public void deletePost(Map<String, Long> postDeleteReq) throws NoSuchElementException, SdkClientException {
        postRepository.findById(postDeleteReq.get("p_id"))
                .ifPresentOrElse((targetPost) -> {
                            System.out.println(targetPost.getImg());
                    s3Util.deleteFile(targetPost.getImg());
                    // cascade 적용이 안됨..!!
                    customCocktailToCocktailRepository.findByCustomCocktail_Id(postDeleteReq.get("p_id"))
                                    .ifPresent(data -> {customCocktailToCocktailRepository.deleteById(data.getId());});
                    postRepository.delete(targetPost);
                },
                () -> {throw new NoSuchElementException("게시글을 찾을 수 없습니다.");}
        );
    }

    public void postReview(PostReviewDTO postReviewDTO) throws Exception {

        Member member = new Member();
        member.setUid(postReviewDTO.getUid());

        Post post = new Post();
        post.setId(postReviewDTO.getP_id());

        PostReview postReview = new PostReview();
        postReview.setMember(member);
        postReview.setPost(post);
        postReview.setReview(postReviewDTO.getReview());

        postReviewRepository.save(postReview);
    }
}
