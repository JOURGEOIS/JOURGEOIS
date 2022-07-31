package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.SearchCocktailDto;
import com.jourgeois.backend.api.dto.SearchKeywordDto;
import com.jourgeois.backend.domain.Material;
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.MaterialRepository;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.SearchKeywordRepository;
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
    private final String s3Url;
    SearchService(SearchKeywordRepository searchKeywordRepository, CocktailRepository cocktailRepository,
                  MemberRepository memberRepository, MaterialRepository materialRepository,
                  @Value("${cloud.aws.s3.bucket.path}") String s3Url){
        this.searchKeywordRepository = searchKeywordRepository;
        this.cocktailRepository = cocktailRepository;
        this.memberRepository = memberRepository;
        this.materialRepository = materialRepository;
        this.s3Url = s3Url;
    }

    public List<SearchCocktailDto> searchByCocktailAll(String name, int page){
        List<SearchCocktailDto> list = new ArrayList<>();
        cocktailRepository.findCocktailBySearch(name, page*10).forEach(data ->
                list.add(SearchCocktailDto.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }
    public List<SearchCocktailDto> searchByCocktail(Long name, int page){
        List<SearchCocktailDto> list = new ArrayList<>();
        cocktailRepository.findByMaterialContaining(name, page*10).forEach(data ->
                list.add(SearchCocktailDto.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }

    public List<ProfileDto> searchByMember(String name, Pageable pageable){
        List<ProfileDto> list = new ArrayList<>();
        memberRepository.findByNicknameContainingOrderByNickname(name, pageable)
                .forEach(data ->
                        list.add(ProfileDto.builder()
                                .id(data.getUid())
                                .email(data.getEmail())
                                .nickname(data.getNickname())
                                .profileImg(s3Url+data.getProfileImg())
                                .introduce(data.getIntroduce()).build()));
        return list;
    }

    public List<SearchKeywordDto> searchKeywords(String name){
        List<SearchKeywordDto> list = new ArrayList<>();
        searchKeywordRepository.findTop5ByNameKrContainingOrderByNameKr(name).forEach(data ->
                list.add(SearchKeywordDto.builder()
                        .id(data.getId())
                        .name(data.getName())
                        .nameKr(data.getNameKr())
                        .type(data.getType()).build()));
        memberRepository.findTop10ByNicknameContainingOrderByNickname(name).forEach(data ->
                list.add(SearchKeywordDto.builder()
                        .id(data.getUid())
                        .nameKr(data.getNickname())
                        .type("계정").build()));
        return list;
    }

    public String searchMaterialName(Long id){
        Material material = materialRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 재료가 없음"));
        return material.getNameKR();
    }
}
