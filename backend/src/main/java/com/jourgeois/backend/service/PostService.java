package com.jourgeois.backend.service;

import com.amazonaws.SdkClientException;
import com.jourgeois.backend.api.dto.*;
import com.jourgeois.backend.domain.*;
import com.jourgeois.backend.repository.*;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    // 원본 칵테일에서 커스텀 칵테일 탭을 눌렀을 때 나오는 목록 반환
    public List<PostInfoDTO> readCumstomCoctailList(Long id, Pageable pageable){
        List<PostInfoDTO> postInfoDTOList = new ArrayList<>();
        customCocktailToCocktailRepository.findByCocktail_Id(id, pageable)
                .forEach(data ->{
                    Long p_id = data.getCustomCocktail().getId();
                    CustomCocktail customCocktail = (CustomCocktail) postRepository.findById(p_id).orElseThrow();
                    Member member = memberRepository.findById(customCocktail.getMember().getUid()).orElseThrow();
                    ProfileDTO profile = new ProfileDTO(member.getUid(), null, member.getName(),
                            member.getNickname(), member.getProfileImg(), null);
                    PostDTO post = PostDTO.builder()
                            .postId(customCocktail.getId())
                            .imgLink(customCocktail.getImg())
                            .description(customCocktail.getDescription())
                            .title(customCocktail.getTitle())
                            .baseCocktail(data.getCocktail().getId())
                            .recipe(customCocktail.getRecipe())
//                            .ingredients(customCocktail.getIngredients())
                            .build();
                    postInfoDTOList.add(new PostInfoDTO(post, profile));
        });
        return postInfoDTOList;
    }

    // 위의 커스텀 칵테일 목록에서 상세보기 했을 때 전체 내용 (댓글 포함) 북마크 했을 때 수정 필요
    public PostInfoDTO readCumstomCoctail(Long p_id){
        CustomCocktail post = (CustomCocktail) postRepository.findById(p_id).get();
        Member member = memberRepository.findById(post.getMember().getUid()).get();
        ProfileDTO p = new ProfileDTO(member.getUid(), member.getEmail(), member.getName(),
                member.getNickname(), member.getProfileImg(), member.getIntroduce());
        PostDTO postpost = PostDTO.builder()
                .postId(post.getId())
                .imgLink(post.getImg())
                .description(post.getDescription())
                .title(post.getTitle())
//                    .baseCocktail(data.getCocktail().getId())
                .ingredients(post.getIngredients())
                .recipe(post.getRecipe())
                .ingredients(post.getIngredients()).build();
        PostInfoDTO result = new PostInfoDTO(postpost, p);

        return result;
    }

    @Transactional
    public void editPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long postId = postDTO.getPostId();
        // post 이미지 업로드
        String uploadURL = s3Util.upload(postDTO.getImg(), postDTO.getUid(), ImgType.POST);

        Member member = new Member();
        member.setUid(postDTO.getUid());
        // 커스텀 칵테일 제목이 Empty라면 일반 게시물이라는 의미
        // postDTO.getTitle().isEmpty() 가끔씩 됨... 이유를 알고 싶다.
        if(postDTO.getTitle()==null || postDTO.getTitle().isEmpty()){
            Post targetPost = postRepository.findByIdAndMember(postId, member).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
            targetPost.setDescription(postDTO.getDescription());

            targetPost.setImg(uploadURL);
            s3Util.deleteFile(targetPost.getImg());
            // post 저장
            postRepository.save(targetPost);
        }else{
            CustomCocktail targetCustomCocktail = (CustomCocktail) postRepository.findByIdAndMember(postId, member).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
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
        Member member = new Member();
        member.setUid(postDeleteReq.get("uid"));
        postRepository.findByIdAndMember(postDeleteReq.get("p_id"), member)
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

    @Transactional
    public void editReview(PostReviewDTO postReviewDTO) throws NoSuchElementException, IllegalArgumentException {
        Member member = new Member();
        member.setUid(postReviewDTO.getUid());

        PostReview postReview = postReviewRepository.findByIdAndMember(postReviewDTO.getPr_id(), member).orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        postReview.setReview(postReviewDTO.getReview());
        postReviewRepository.save(postReview);
    }

    @Transactional
    public void deleteReview(Map<String, Long> reviewDeleteReq) throws NoSuchElementException, IllegalArgumentException {
        Member member = new Member();
        member.setUid(reviewDeleteReq.get("uid"));

        PostReview postReview = postReviewRepository.findByIdAndMember(reviewDeleteReq.get("pr_id"), member).orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        postReviewRepository.delete(postReview);
    }
}
