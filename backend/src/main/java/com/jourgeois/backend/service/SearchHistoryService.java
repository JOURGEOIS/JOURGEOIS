package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.search.SearchHistoryDTO;
import com.jourgeois.backend.domain.search.SearchHistory;
import com.jourgeois.backend.repository.SearchHistoryRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Async
    public void writeSearchHistory(String keyword) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKeyword(keyword);
        this.searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistoryDTO> getHotKeyword() {
        return this.searchHistoryRepository.getHotKeyword();
    }

    public List<SearchHistoryDTO> getWeeklyHotKeyword() {
        return this.searchHistoryRepository.getWeeklyHotKeyword();
    }
}
