package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.SearchCocktailDto;
import com.jourgeois.backend.api.dto.SearchFilterDto;
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
import java.util.Locale;
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
        memberRepository.findByNicknameContainingIgnoreCaseOrderByNickname(name, pageable)
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
        searchKeywordRepository.findTop10ByKeywordContainingOrderByNameKr(name.toLowerCase()).forEach(data ->
                list.add(SearchKeywordDto.builder()
                        .id(data.getId())
                        .name(data.getName())
                        .nameKr(data.getNameKr())
                        .type(data.getType()).build()));
        memberRepository.findTop10ByNicknameContainingIgnoreCaseOrderByNickname(name).forEach(data ->
                list.add(SearchKeywordDto.builder()
                        .id(data.getUid())
                        .nameKr(data.getNickname())
                        .type("계정").build()));
        return list;
    }

    public List<SearchCocktailDto> CocktailList(Pageable pageable){
        List<SearchCocktailDto> list = new ArrayList<>();
        cocktailRepository.findAllByOrderById(pageable).forEach(data ->
                list.add(SearchCocktailDto.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }

    public int filterCount(SearchFilterDto searchFilterDto){
        String type = searchFilterDto.getType()==1 ? "Alcoholic" : "Non alcoholic";
        return cocktailRepository.findByFilter(type, searchFilterDto.getAbv()[0], searchFilterDto.getAbv()[1],
                searchFilterDto.getMaterials(), searchFilterDto.getMaterials().size());
    }

    public List<SearchCocktailDto> filterList(SearchFilterDto searchFilterDto){
        String type = searchFilterDto.getType()==1 ?  "Alcoholic" : "Non alcoholic";
        List<SearchCocktailDto> list = new ArrayList<>();
        cocktailRepository.findByFilterInfo(type, searchFilterDto.getAbv()[0], searchFilterDto.getAbv()[1],
                        searchFilterDto.getMaterials(), searchFilterDto.getMaterials().size()).forEach(data ->
                list.add(SearchCocktailDto.builder()
                        .id(data.getId())
                        .img(data.getImg())
                        .name(data.getNameKR())
                        .alcohol(data.getAlcohol())
                        .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }
    public String searchMaterialName(Long id){
        Material material = materialRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 재료가 없음"));
        return material.getNameKR();
    }
}
