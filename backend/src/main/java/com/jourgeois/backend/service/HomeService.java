package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.home.HomeCocktailItemDTO;
import com.jourgeois.backend.api.dto.home.HomeCocktailItemVO;
import com.jourgeois.backend.repository.ClipRepository;
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.PostRepository;
import com.jourgeois.backend.util.TagType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private final CocktailRepository cocktailRepository;
    private final PostRepository postRepository;
    private final ClipRepository clipRepository;
    private final String s3Url;


    public HomeService(CocktailRepository cocktailRepository, PostRepository postRepository, ClipRepository clipRepository, @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.cocktailRepository = cocktailRepository;
        this.postRepository = postRepository;
        this.clipRepository = clipRepository;
        this.s3Url = s3Url;
    }

    public List<HomeCocktailItemDTO> recommenderLiquor(Long uid, Pageable pageable) throws Exception {
        List<HomeCocktailItemVO> recommendCocktail = cocktailRepository.findByrecommenderLiquor(uid, pageable);
        return convertHomeCocktailItemVO2DTO(recommendCocktail);
    }

    public List<HomeCocktailItemDTO> recommender5Liquor(Long uid) throws Exception {
        List<HomeCocktailItemVO> recommendCocktail = cocktailRepository.findByrecommender5Liquor(uid);
        return convertHomeCocktailItemVO2DTO(recommendCocktail);
    }

    // 신규 커스텀 칵테일 5개 반환
    public List<HomeCocktailItemDTO> getLatestCustomCocktail(Pageable pageable) throws Exception{
        List<HomeCocktailItemVO> customCocktails = postRepository.findCustomCocktailOrderByCreateTime(pageable);
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }
    public List<HomeCocktailItemDTO> getLatestTop5CustomCocktail() throws Exception {
        List<HomeCocktailItemVO> customCocktails = postRepository.findTop5CustomCocktailOrderByCreateTime();
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public List<HomeCocktailItemDTO> getHotTop5Cocktail() throws Exception {
        List<HomeCocktailItemVO> customCocktails = cocktailRepository.findTop5CocktailOrderByBookmarked();
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public List<HomeCocktailItemDTO> getHotCocktail(Pageable pageable) throws Exception {
        List<HomeCocktailItemVO> customCocktails = cocktailRepository.findCocktailOrderByBookmarked(pageable);
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    private List<HomeCocktailItemDTO> convertHomeCocktailItemVO2DTO(List<HomeCocktailItemVO> homeCocktails) throws Exception {
        List<HomeCocktailItemDTO> homeCocktailsResponse = new ArrayList<>();

        homeCocktails.forEach((customCocktail) -> {
            String img = customCocktail.getImg().contains("https://") ? customCocktail.getImg() : s3Url + customCocktail.getImg();

            HomeCocktailItemDTO cocktailItem = HomeCocktailItemDTO.builder()
                    .type(customCocktail.getType())
                    .cocktailId(customCocktail.getCocktailId())
                    .img(img)
                    .title(customCocktail.getTitle())
                    .baseCocktailId(customCocktail.getBaseCocktailId())
                    .base(customCocktail.getBase())
                    .abv(customCocktail.getAbv())
                    .build();

            homeCocktailsResponse.add(cocktailItem);
        });

        return homeCocktailsResponse;
    }

    public List<HomeCocktailItemDTO> getWeeklyHotCustomCocktail(Pageable pageable) throws Exception {
        List<HomeCocktailItemVO> customCocktails = postRepository.getWeeklyHotCustomCocktail(pageable);
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public List<HomeCocktailItemDTO> getWeeklyHot5CustomCocktail() throws Exception {
        List<HomeCocktailItemVO> customCocktails = postRepository.getWeeklyHot5CustomCocktail();
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public String getRandomClip() {
        return clipRepository.findRandomClip();
    }

    public List<HomeCocktailItemDTO> getTag5Cocktail(TagType tagType) throws Exception {
        List<HomeCocktailItemVO> customCocktails = cocktailRepository.getTag5Cocktail(tagType.getValue());
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public List<HomeCocktailItemDTO> getTagCocktail(TagType tagType, Pageable pageable) throws Exception {
        List<HomeCocktailItemVO> customCocktails = cocktailRepository.getTagCocktail(tagType.getValue(), pageable);
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }
}
