package com.jourgeois.backend.service;

import com.amazonaws.SdkClientException;


import com.jourgeois.backend.api.dto.member.FollowerDTO;
import com.jourgeois.backend.api.dto.post.*;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.member.FollowPK;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.*;

import com.jourgeois.backend.repository.*;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CustomCocktailToCocktailRepository customCocktailToCocktailRepository;
    private final CocktailRepository cocktailRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final PostReviewRepository postReviewRepository;
    private final FollowRepository followRepository;
    private final S3Util s3Util;
    private final String s3Url;

    @Autowired
    public PostService(PostRepository postRepository,
                       MemberRepository memberRepository,
                       CustomCocktailToCocktailRepository customCocktailToCocktailRepository, CocktailRepository cocktailRepository, PostBookmarkRepository postBookmarkRepository, PostReviewRepository postReviewRepository, FollowRepository followRepository, S3Util s3Util,
                       @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.customCocktailToCocktailRepository = customCocktailToCocktailRepository;
        this.cocktailRepository = cocktailRepository;
        this.postBookmarkRepository = postBookmarkRepository;
        this.postReviewRepository = postReviewRepository;
        this.followRepository = followRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
    }

    @Transactional
    public Long postPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long writerId = postDTO.getUid();

        Member member = memberRepository.findById(writerId)
                            .orElseThrow(()->new NoSuchElementException("유저를 찾을 수 없습니다."));
        if(postDTO.getTitle()==null || postDTO.getTitle().isEmpty()){
            // 공백 처리
            if(postDTO.getDescription() == null || postDTO.getDescription().trim().isEmpty()) {
                throw  new IllegalArgumentException("필수 정보 누락");
            }


            Post post = new Post();
            post.setDescription(postDTO.getDescription());
            post.setMember(member);
            // post 이미지 업로드
            String uploadURL = s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);

            post.setImg(uploadURL);

            // post 저장
            return postRepository.save(post).getId();
        }else{
            // 제목, 커스텀 칵테일의 경우 재료, 레시피 공백 예외 처리
            if(postDTO.getTitle().trim().isEmpty() || postDTO.getIngredients() == null || postDTO.getIngredients().length == 0 || postDTO.getRecipe().trim().isEmpty()){
                throw  new IllegalArgumentException("필수 정보 누락");
            }

            CustomCocktail cc = new CustomCocktail();
            cc.setTitle(postDTO.getTitle().trim());
            cc.setDescription(postDTO.getDescription().trim());
            cc.setMember(member);
            cc.setIngredients(Arrays.toString(postDTO.getIngredients()).replace("[","").replace("]",""));
            cc.setRecipe(postDTO.getRecipe());
            // post 이미지 업로드
            String uploadURL = s3Util.upload(postDTO.getImg(), member.getUid(), ImgType.POST);

            cc.setImg(uploadURL);
            // post 저장
            CustomCocktail cocktail = postRepository.save(cc);

            // 베이스가 있는 커스텀 칵테일이라면
            if(postDTO.getBaseCocktail()!=null){
                Cocktail ori = cocktailRepository.findById(postDTO.getBaseCocktail()).get();

                customCocktailToCocktailRepository.findById(new CustomCocktailId(cocktail.getId(), ori.getId()))
                                .ifPresentOrElse(data -> {new Exception("BaseCocktail  save Error");},
                                        ()->{customCocktailToCocktailRepository.save(new CustomCocktailToCocktail(cocktail, ori));});
            }

            return cocktail.getId();
        }
    }

    public String postImageLocalUpload(PostDTO postDTO) throws IOException {
        Long uid = postDTO.getUid();
        String url = s3Util.localUpload(postDTO.getImg(), uid, ImgType.POST);
        return url;
    }

    @Transactional
    public Long editPost(PostDTO postDTO) throws IOException, NoSuchElementException {
        Long postId = postDTO.getPostId();

        Member member = new Member();
        member.setUid(postDTO.getUid());

        // 커스텀 칵테일 제목이 Empty라면 일반 게시물이라는 의미
        // postDTO.getTitle().isEmpty() 가끔씩 됨... 이유를 알고 싶다.
        if(postDTO.getTitle()==null || postDTO.getTitle().isEmpty()){
            Post targetPost = postRepository.findByIdAndMember(postId, member).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
            targetPost.setDescription(postDTO.getDescription());

            if(postDTO.getDescription() == null || postDTO.getDescription().trim().isEmpty()) {
                throw  new IllegalArgumentException("필수 정보 누락");
            }

            // post 이미지 업로드
            if(postDTO.getImg() != null && !postDTO.getImg().getOriginalFilename().isEmpty()){
                String uploadURL = s3Util.upload(postDTO.getImg(), postDTO.getUid(), ImgType.POST);
                targetPost.setImg(uploadURL);
                s3Util.deleteFile(targetPost.getImg());
            }

            // post 저장
            return postRepository.save(targetPost).getId();
        }else{
            // 커스텀 칵테일의 경우 재료, 레시피 공백 예외 처리
            if(postDTO.getTitle().trim().isEmpty() || postDTO.getIngredients() == null || postDTO.getIngredients().length == 0 || postDTO.getRecipe().trim().isEmpty()){
                throw  new IllegalArgumentException("필수 정보 누락");
            }

            CustomCocktail targetCustomCocktail = (CustomCocktail) postRepository.findByIdAndMember(postId, member).orElseThrow(()->new NoSuchElementException("게시글을 찾을 수 없습니다."));
            targetCustomCocktail.setDescription(postDTO.getDescription());

            // post 이미지 업로드
            if(postDTO.getImg() != null && !postDTO.getImg().getOriginalFilename().isEmpty()){
                String uploadURL = s3Util.upload(postDTO.getImg(), postDTO.getUid(), ImgType.POST);
                targetCustomCocktail.setImg(uploadURL);
                s3Util.deleteFile(targetCustomCocktail.getImg());
            }

            targetCustomCocktail.setTitle(postDTO.getTitle().trim());
            targetCustomCocktail.setRecipe(postDTO.getRecipe().trim());
            targetCustomCocktail.setIngredients(Arrays.toString(postDTO.getIngredients()).replace("[","").replace("]",""));
            // post 저장
            return postRepository.save(targetCustomCocktail).getId();
        }

    }

    @Transactional
    public void deletePost(Map<String, Long> postDeleteReq) throws NoSuchElementException, SdkClientException {
        Member member = new Member();
        member.setUid(postDeleteReq.get("uid"));
        postRepository.findByIdAndMember(postDeleteReq.get("postId"), member)
                .ifPresentOrElse((targetPost) -> {
                            System.out.println(targetPost.getImg());
                    s3Util.deleteFile(targetPost.getImg());
                    // cascade 적용이 안됨..!!
                    customCocktailToCocktailRepository.findByCustomCocktailId(new CustomCocktail(postDeleteReq.get("postId")))
                                    .ifPresent(data -> {customCocktailToCocktailRepository.deleteByCustomCocktailId(new CustomCocktail(data.getCustomCocktailId()));});
                    postRepository.delete(targetPost);
                },
                () -> {throw new NoSuchElementException("게시글을 찾을 수 없습니다.");}
        );
    }

    public Map<String, Object> postReview(PostReviewDTO postReviewDTO) throws Exception {

        Member member = new Member();
        member.setUid(postReviewDTO.getUid());

        Post post = new Post();
        post.setId(postReviewDTO.getPostId());

        PostReview postReview = new PostReview();
        postReview.setMember(member);
        postReview.setPost(post);
        postReview.setReview(postReviewDTO.getReview());

        PostReview newReview = postReviewRepository.save(postReview);

        Map<String, Object> response = new HashMap<>();
        response.put("postId", newReview.getPost().getId());
        response.put("reviewCount", postReviewRepository.countByPost(post));

        return response;
    }

    @Transactional
    public Map<String, Object> editReview(PostReviewDTO postReviewDTO) throws NoSuchElementException, IllegalArgumentException {
        Member member = new Member();
        member.setUid(postReviewDTO.getUid());

        PostReview postReview = postReviewRepository.findByIdAndMember(postReviewDTO.getPostReviewId(), member).orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        postReview.setReview(postReviewDTO.getReview());
        PostReview newPostReview = postReviewRepository.save(postReview);


        Map<String, Object> response = new HashMap<>();
        response.put("postId", newPostReview.getPost().getId());
        response.put("reviewCount", postReviewRepository.countByPost(newPostReview.getPost()));

        return response;
    }

    @Transactional
    public Map<String, Object> deleteReview(Map<String, Long> reviewDeleteReq) throws NoSuchElementException, IllegalArgumentException {
        Member member = new Member();
        member.setUid(reviewDeleteReq.get("uid"));

        PostReview postReview = postReviewRepository.findByIdAndMember(reviewDeleteReq.get("postReviewId"), member).orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        postReviewRepository.delete(postReview);

        Map<String, Object> response = new HashMap<>();
        response.put("postId", postReview.getPost().getId());
        response.put("reviewCount", postReviewRepository.countByPost(postReview.getPost()));
        return response;
    }

    public List<PostReviewResponseDTO> getReviewAll(Long uid, Long p_id, Boolean asc, Pageable pageable) throws Exception {
        if(asc) pageable.getSort().ascending();
        else pageable.getSort().descending();

        List<PostReviewResponseVO> reviews = asc ? postReviewRepository.getAllPostReviewsAsc(uid, p_id, pageable) : postReviewRepository.getAllPostReviewsDesc(uid, p_id, pageable);
        List<PostReviewResponseDTO> response = new LinkedList<>();

        reviews.forEach((review) -> {
            PostReviewResponseDTO postReviewResponse = PostReviewResponseDTO.builder()
                    .postReviewId(review.getPr_id())
                    .uid(review.getUid())
                    .nickname(review.getNickname())
                    .review(review.getReview())
                    .profileImg(s3Url + review.getProfileImg())
                    .isUpdated(review.getIsUpdated())
                    .createTime(review.getCreateTime())
                    .updateTime(review.getUpdateTime())
                    .isMine(review.getIsMine())
                    .build();

            response.add(postReviewResponse);

        });

        return response;
    }

    @Transactional
    public boolean pushBookmark(Map<String, Long> bookmark){
        Long m_id = bookmark.get("uid");
        Long p_id = bookmark.get("postId");

        PostBookmarkId key = new PostBookmarkId(m_id, p_id);

        Member member = new Member();
        member.setUid(m_id);
        Post post = new Post();
        post.setId(p_id);

        postBookmarkRepository.findById(key).ifPresentOrElse(data ->{
            postBookmarkRepository.deleteById(key);},
                ()->{postBookmarkRepository.save(new PostBookmark(member, post));});

        return checkUserBookmark(key);
    }

    public boolean checkUserBookmark(PostBookmarkId key){
        return postBookmarkRepository.findById(key).isPresent();
    }

    public boolean checkPostId(Long id){
        return postRepository.findById(id).isPresent();
    }

    public Integer countPostBookmark(Long p_id){
        return postBookmarkRepository.countByPostId(new Post(p_id));
    }

    public List<FollowerDTO> getLikeList(Long uid, Long p_id, Pageable pageable){
        List<FollowerDTO> followersResponse = new ArrayList<>();

        // p_id를 북마크한 사람들 목록 가져오기
        postBookmarkRepository.findByPostId(new Post(p_id), pageable).forEach(data -> {
            Member member = memberRepository.findById(data.getMemberId().getUid()).orElseThrow();
            FollowPK key = new FollowPK(uid, member.getUid());

            Integer status = followRepository.findById(key).isPresent() ? 1 : 0;
            if(uid.equals(member.getUid())) {
                status = -1;
            }
            
            followersResponse.add(FollowerDTO.builder()
                    .introduce(member.getIntroduce())
                    .isFollowed(status)
                    .nickname(member.getNickname())
                    .uid(member.getUid())
                    .profileImg(s3Url+member.getProfileImg())
                    .build());
        });
        return followersResponse;
    }

    public PostInfoDTO readCustomCocktail(Long p_id, Long uid){
        Post post = postRepository.findById(p_id).orElseThrow();
        PostDTO postDTO = PostDTO.builder().postId(post.getId())
                .imgLink(s3Url + post.getImg())
                .description(post.getDescription())
                .createTime(post.getCreateTime())
                .lastUpdateTime(post.getLastUpdateTime())
                .isUpdated(post.getCreateTime().isBefore(post.getLastUpdateTime()) ? 1 : 0) // 수정됐으면 1
                .like(postBookmarkRepository.countByPostId(new Post(p_id)))
                .ilike(postBookmarkRepository.findById(new PostBookmarkId(uid, post.getId())).isPresent())
                .reviewCount(postReviewRepository.countByPost(new Post(p_id)))
                .build();

        if(post.getD_type().equals("cocktail")){
            CustomCocktail cocktail = (CustomCocktail) post;
            postDTO.setTitle(cocktail.getTitle());
            postDTO.setIngredients(cocktail.getIngredients()!=null ? cocktail.getIngredients().split(", ") : null);
            postDTO.setRecipe(cocktail.getRecipe());
            customCocktailToCocktailRepository.findByCustomCocktailId(new CustomCocktail(p_id))
                    .ifPresent(data -> {
                        postDTO.setBaseCocktail(data.getCocktailId().getId());
                        postDTO.setBaseCocktailName(cocktailRepository.findById(data.getCocktailId().getId()).get().getNameKR());
                    });
        }

        // uid 는 본인 post의 주인과 비교
        Member member = memberRepository.findById(post.getMember().getUid()).orElseThrow();
        FollowPK key = new FollowPK(uid, member.getUid());
        Integer status = followRepository.findById(key).isPresent() ? 1 : 0;
        if(uid.equals(member.getUid())) {
            status = -1;
        }

        FollowerDTO profile = FollowerDTO.builder()
                .uid(member.getUid())
                .profileImg(s3Url + member.getProfileImg())
                .nickname(member.getNickname())
                .isFollowed(status)
                .introduce(member.getIntroduce())
                .build();

        return PostInfoDTO.builder().followerDTO(profile).customCocktail(postDTO).build();
    }

    public List<NewsFeedDTO> getNewsFeed(Long me, Pageable pageable) {
        List<NewsFeedVO> feeds = postRepository.getNewsFeed(me, pageable);

        List<NewsFeedDTO> feedResponse = new ArrayList<>();
        feeds.forEach((feed) -> {
            NewsFeedDTO feedDTO = NewsFeedDTO.builder()
                    .createTime(feed.getCreateTime())
                    .updateTime(feed.getUpdateTime())
                    .isUpdated(feed.getIsUpdated())
                    .postId(feed.getPid())
                    .type(feed.getType())
                    .writer(feed.getWriter())
                    .nickname(feed.getNickname())
                    .profileImg(s3Url + feed.getProfileImg())
                    .isSuperCustomCocktail(feed.getIsSuperCustomCocktail())
                    .baseCocktailId(feed.getBaseCocktailId())
                    .baseCocktailName(feed.getBaseCocktailName())
                    .cocktailTitle(feed.getCocktailTitle())
                    .postImg(s3Url + feed.getPostImg())
                    .description(feed.getDescription())
                    .followerCount(feed.getFollowerCount())
                    .reviewCount(feed.getReviewCount())
                    .likeCount(feed.getLikeCount())
                    .isLiked(feed.getIsLiked())
                    .build();

            feedResponse.add(feedDTO);
        });

        return feedResponse;
    }
}
