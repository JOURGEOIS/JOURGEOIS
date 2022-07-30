package com.jourgeois.backend.util.scheduler;

import com.jourgeois.backend.api.dto.SearchHistoryDto;
import com.jourgeois.backend.api.dto.SearchHistoryVO;
import com.jourgeois.backend.api.dto.SearchTrendDto;
import com.jourgeois.backend.service.RedisService;
import com.jourgeois.backend.service.SearchHistoryService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
public class SearchStatistics {
    private static final String PREV_TIME = "prev";

    private static final String CUR_TIME = "cur";

    public SearchStatistics(SearchHistoryService searchHistoryService, RedisService redisService) {
        this.searchHistoryService = searchHistoryService;
        this.redisService = redisService;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final SearchHistoryService searchHistoryService;

    private final RedisService redisService;

    @Scheduled(cron = "0 */1 * * * *")
    public void getHotKeywordEveryTemMinutes() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.now().minusMinutes(5L);
        String toFormatted = to.format(DATE_TIME_FORMATTER);
        String fromFormatted = from.format(DATE_TIME_FORMATTER);
        try {
            List<SearchHistoryDto> jpa_cur_hot = this.searchHistoryService.getHotKeyword();
            List<SearchHistoryVO> cur_hot = new ArrayList<>();
            for (SearchHistoryDto s : jpa_cur_hot) {
                SearchHistoryVO searchHistoryVO = new SearchHistoryVO();
                searchHistoryVO.setKeyword(s.getKeyword());
                searchHistoryVO.setHits(s.getHits());
                cur_hot.add(searchHistoryVO);
            }
            SearchTrendDto searchTrend = new SearchTrendDto();
            searchTrend.setFrom(fromFormatted);
            searchTrend.setTo(toFormatted);
            searchTrend.setKeywords(cur_hot);
            searchTrend.setDelta(new ArrayList());
            System.out.println("현재 로그 조회 성공");
            SearchTrendDto prev = redisService.getHotKeywords("cur");

            System.out.println("이전 로그 조회 성공");
            if (prev == null) {
                System.out.println("통계가 없음");
                for (int i = 0; i < cur_hot.size(); i++)
                    searchTrend.getDelta().add(Integer.valueOf(-1));
                redisService.setHotKeywords("prev", searchTrend);
                redisService.setHotKeywords("cur", searchTrend);
            } else {
                this.redisService.setHotKeywords("prev", prev);
                List<SearchHistoryVO> prev_hot = prev.getKeywords();
                List<String> prev_hot_keyword = new ArrayList<>();
                prev_hot.forEach(history -> prev_hot_keyword.add(history.getKeyword()));
                List<String> cur_hot_keyword = new ArrayList<>();
                cur_hot.forEach(history -> cur_hot_keyword.add(history.getKeyword()));
                for (int cur_rank = 0; cur_rank < cur_hot.size(); cur_rank++) {
                    int prev_rank = prev_hot_keyword.indexOf(cur_hot_keyword.get(cur_rank));
                    if (prev_rank < 0) {
                        searchTrend.getDelta().add(Integer.valueOf(-5));
                    } else {
                        searchTrend.getDelta().add(Integer.valueOf(prev_rank - cur_rank));
                    }
                }
                System.out.println(searchTrend.getFrom());
                redisService.setHotKeywords("cur", searchTrend);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}