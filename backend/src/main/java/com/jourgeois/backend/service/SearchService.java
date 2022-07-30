package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.SearchCocktailDto;
import com.jourgeois.backend.api.dto.SearchKeywordDto;
import com.jourgeois.backend.repository.CocktailRepository;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.SearchKeywordRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final SearchKeywordRepository searchKeywordRepository;
    private final CocktailRepository cocktailRepository;
    private final MemberRepository memberRepository;
    SearchService(SearchKeywordRepository searchKeywordRepository, CocktailRepository cocktailRepository,
                  MemberRepository memberRepository){
        this.searchKeywordRepository = searchKeywordRepository;
        this.cocktailRepository = cocktailRepository;
        this.memberRepository = memberRepository;
    }

    public List<SearchCocktailDto> searchByCocktail(String name, Pageable pageable){
        List<SearchCocktailDto> list = new ArrayList<>();
        cocktailRepository.findByNameContainingOrderByName(name, pageable).forEach(data ->
                list.add(SearchCocktailDto.builder()
                .id(data.getId())
                .name(data.getName())
                .alcohol(data.getAlcohol())
                .baseLiquor(data.getBaseLiquor()).build()));
        return list;
    }

    public List<ProfileDto> searchByMember(String name, Pageable pageable){
        List<ProfileDto> list = new ArrayList<>();
        memberRepository.findByNicknameContainingOrderByNickname(name, pageable)
                .forEach(data ->
                        list.add(ProfileDto.builder()
                                .nickname(data.getNickname())
                                .profileImg(data.getProfileImg())
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
}
