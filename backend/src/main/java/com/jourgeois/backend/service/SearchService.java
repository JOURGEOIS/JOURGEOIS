package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.member.FollowerDTO;
import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.post.PostSearchMaterialDTO;
import com.jourgeois.backend.api.dto.search.SearchCocktailDTO;
import com.jourgeois.backend.api.dto.search.SearchFilterDTO;
import com.jourgeois.backend.api.dto.search.SearchKeywordDTO;
import com.jourgeois.backend.domain.cocktail.Material;
import com.jourgeois.backend.domain.member.FollowPK;
import com.jourgeois.backend.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SearchService {
    private final SearchKeywordRepository searchKeywordRepository;
    private final CocktailRepository cocktailRepository;
    private final MemberRepository memberRepository;
    private final MaterialRepository materialRepository;
    private final FollowRepository followRepository;
    private final String s3Url;
    SearchService(SearchKeywordRepository searchKeywordRepository, CocktailRepository cocktailRepository,
                  MemberRepository memberRepository, MaterialRepository materialRepository,
                  FollowRepository followRepository, @Value("${cloud.aws.s3.bucket.path}") String s3Url){
        this.searchKeywordRepository = searchKeywordRepository;
        this.cocktailRepository = cocktailRepository;
        this.memberRepository = memberRepository;
        this.materialRepository = materialRepository;
        this.followRepository = followRepository;
        this.s3Url = s3Url;
    }

    public List<SearchCocktailDTO> searchByCocktailAll(String name, int page){
        List<SearchCocktailDTO> list = new ArrayList<>();
        cocktailRepository.findCocktailBySearch(name, page*15).forEach(data ->
                list.add(SearchCocktailDTO.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }
    public List<SearchCocktailDTO> searchByCocktail(Long name, int page){
        List<SearchCocktailDTO> list = new ArrayList<>();
        cocktailRepository.findByMaterialContaining(name, page*15).forEach(data ->
                list.add(SearchCocktailDTO.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }

    public List<FollowerDTO> searchByMember(Long uid, String name, Pageable pageable){
            List<FollowerDTO> list = new ArrayList<>();
            memberRepository.findByNicknameContainingIgnoreCaseOrderByNickname(name, pageable)
                .forEach(data ->{
                    FollowPK key = new FollowPK(uid, data.getUid());
                    Integer status = followRepository.findById(key).isPresent() ? 1 : 0;
                    if(uid.equals(data.getUid())) {
                        status = -1;
                    }

                    FollowerDTO f = FollowerDTO.builder()
                            .isFollowed(status)
                            .nickname(data.getNickname())
                            .uid(data.getUid())
                            .profileImg(s3Url + data.getProfileImg())
                            .introduce(data.getIntroduce()).build();
                            MemberDTO p = new MemberDTO(data.getUid(), data.getEmail(), data.getName(),
                                    data.getNickname(), s3Url + data.getProfileImg(), data.getIntroduce());
                            list.add(f);
                        });
        return list;
    }

    public List<SearchKeywordDTO> searchKeywords(String name){
        List<SearchKeywordDTO> list = new ArrayList<>();
        searchKeywordRepository.findTop10ByNameContainingOrNameKrContainingOrderByNameKr(name, name).forEach(data ->
                list.add(SearchKeywordDTO.builder()
                        .id(data.getId())
                        .name(data.getName())
                        .nameKr(data.getNameKr())
                        .type(data.getType()).build()));
        memberRepository.findTop10ByNicknameContainingIgnoreCaseOrderByNickname(name).forEach(data ->
                list.add(SearchKeywordDTO.builder()
                        .id(data.getUid())
                        .nameKr(data.getNickname())
                        .type("계정").build()));
        return list;
    }

    public List<SearchCocktailDTO> CocktailList(Pageable pageable){
        List<SearchCocktailDTO> list = new ArrayList<>();
        cocktailRepository.findAllByOrderById(pageable).forEach(data ->
                list.add(SearchCocktailDTO.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }

    public int filterCount(SearchFilterDTO searchFilterDto){
        String type = searchFilterDto.getType()==1 ? "Alcoholic" : "Non alcoholic";
        int high = searchFilterDto.getAbv()[1]==30 ? 100:searchFilterDto.getAbv()[1];
        if(searchFilterDto.getMaterials().size()==0){
            return cocktailRepository.countByTypeAndAlcoholBetween(type, searchFilterDto.getAbv()[0], high);
        }else {
            return cocktailRepository.findByFilter(type, searchFilterDto.getAbv()[0], high,
                    searchFilterDto.getMaterials(), searchFilterDto.getMaterials().size());
        }
    }

    public List<SearchCocktailDTO> filterList(SearchFilterDTO searchFilterDto){
        String type = searchFilterDto.getType()==1 ?  "Alcoholic" : "Non alcoholic";
        int high = searchFilterDto.getAbv()[1]==30 ? 100:searchFilterDto.getAbv()[1];
        List<SearchCocktailDTO> list = new ArrayList<>();
        if(searchFilterDto.getMaterials().size()==0){
            cocktailRepository.findByTypeAndAlcoholBetween(type, searchFilterDto.getAbv()[0], high
                    ,searchFilterDto.getPage()*15, 15)
                    .forEach(data ->
                            list.add(SearchCocktailDTO.builder()
                                    .id(data.getId())
                                    .img(data.getImg())
                                    .name(data.getNameKR())
                                    .alcohol(data.getAlcohol())
                                    .baseLiquor(data.getBaseLiquor()).build()));
        }else{
            cocktailRepository.findByFilterInfo(type, searchFilterDto.getAbv()[0], high,
                    searchFilterDto.getMaterials(), searchFilterDto.getMaterials().size(),searchFilterDto.getPage()*15).forEach(data ->
                    list.add(SearchCocktailDTO.builder()
                            .id(data.getId())
                            .img(data.getImg())
                            .name(data.getNameKR())
                            .alcohol(data.getAlcohol())
                            .baseLiquor(data.getBaseLiquor()).build()));
        }

        return list;
    }
    public String searchMaterialName(Long id){
        Material material = materialRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 재료가 없음"));
        return material.getNameKR();
    }

    public List<PostSearchMaterialDTO> searchMaterialList(String keywrod, Pageable pageable){
        List<PostSearchMaterialDTO> list = new ArrayList<>();
        materialRepository.findByNameKRContainingOrNameContaining(keywrod, keywrod, pageable).forEach(data ->{
            list.add(PostSearchMaterialDTO.builder()
                    .name(data.getNameKR())
                    .img(data.getImg()).build());
        });
        return list;
    }
}
