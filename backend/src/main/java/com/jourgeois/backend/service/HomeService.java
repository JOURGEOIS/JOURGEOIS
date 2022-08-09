package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.home.HomeCocktailItemDTO;
import com.jourgeois.backend.api.dto.home.HomeCocktailItemVO;
import com.jourgeois.backend.api.dto.search.SearchCocktailDTO;
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private final CocktailRepository cocktailRepository;
    private final PostRepository postRepository;
    private final String s3Url;

    public HomeService(CocktailRepository cocktailRepository, PostRepository postRepository, @Value("${cloud.aws.s3.bucket.path}") String s3Url) {
        this.cocktailRepository = cocktailRepository;
        this.postRepository = postRepository;
        this.s3Url = s3Url;
    }

    public List<SearchCocktailDTO> recommenderLiquor(Long uid, Pageable pageable){
        List<SearchCocktailDTO> list = new ArrayList<>();
        cocktailRepository.findByrecommenderLiquor(uid, pageable).forEach(data -> {
            list.add(SearchCocktailDTO.builder()
                    .id(data.getId())
                    .img(data.getImg())
                    .name(data.getNameKR())
                    .alcohol(data.getAlcohol())
                    .baseLiquor(data.getBaseLiquor()).build());
        });
        return list;
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
        List<HomeCocktailItemVO> customCocktails = postRepository.findTop5CocktailOrderByBookmarked();
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    public List<HomeCocktailItemDTO> getHotCocktail(Pageable pageable) throws Exception {
        List<HomeCocktailItemVO> customCocktails = postRepository.findCocktailOrderByBookmarked(pageable);
        return convertHomeCocktailItemVO2DTO(customCocktails);
    }

    private List<HomeCocktailItemDTO> convertHomeCocktailItemVO2DTO(List<HomeCocktailItemVO> customCocktails) throws Exception {
        List<HomeCocktailItemDTO> customCocktailsResponse = new ArrayList<>();

        customCocktails.forEach((customCocktail) -> {
            String img = customCocktail.getImg().contains("https://") ? customCocktail.getImg() : s3Url + customCocktail.getImg();

            HomeCocktailItemDTO cocktailItem = HomeCocktailItemDTO.builder()
                    .cocktailId(customCocktail.getCocktailId())
                    .img(img)
                    .title(customCocktail.getTitle())
                    .base(customCocktail.getBase())
                    .abv(customCocktail.getAbv())
                    .build();

            customCocktailsResponse.add(cocktailItem);
        });

        return customCocktailsResponse;
    }
}
