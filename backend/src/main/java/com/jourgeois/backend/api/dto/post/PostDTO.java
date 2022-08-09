package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.ToString;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@ToString
@Builder
public class PostDTO {
    // 게시글 공통
    private Long postId;
    private Long uid;
    private MultipartFile img;
    private String imgLink;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    private Integer isUpdated;
    private Integer like;
    private Boolean ilike;
    private Integer reviewCount;
    private Boolean isAgree;

    // 커스텀 칵테일
    private String title;
    private Long baseCocktail;
    private String baseCocktailName;
    private String[] ingredients;
    private String recipe;
    private Integer type;

    public Boolean getAgree() {
        return isAgree;
    }

    public void setAgree(Boolean agree) {
        isAgree = agree;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getIlike() {
        return ilike;
    }

    public void setIlike(Boolean ilike) {
        this.ilike = ilike;
    }

    public Integer getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(Integer isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getBaseCocktailName() {
        return baseCocktailName;
    }

    public void setBaseCocktailName(String baseCocktailName) {
        this.baseCocktailName = baseCocktailName;
    }
    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBaseCocktail() {
        return baseCocktail;
    }

    public void setBaseCocktail(Long baseCocktail) {
        this.baseCocktail = baseCocktail;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
