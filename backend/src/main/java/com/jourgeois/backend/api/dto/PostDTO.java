package com.jourgeois.backend.api.dto;

import lombok.Builder;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Builder
public class PostDTO {
    // 게시글 공통
    private Long postId;
    private Long uid;
    private MultipartFile img;
    private String imgLink;
    private String title;
    private String description;
    
    // 커스텀 칵테일
    private Long baseCocktail;
    private String ingredients;
    private String recipe;

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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
