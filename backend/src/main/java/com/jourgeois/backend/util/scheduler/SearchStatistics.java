package com.jourgeois.backend.util.scheduler;

import com.jourgeois.backend.api.dto.home.HomeCocktailItemDTO;
import com.jourgeois.backend.api.dto.home.HomeCocktailItemVO;
import com.jourgeois.backend.api.dto.search.SearchHistoryDTO;
import com.jourgeois.backend.api.dto.search.SearchHistoryVO;
import com.jourgeois.backend.api.dto.search.SearchTrendDto;
import com.jourgeois.backend.service.HomeService;
import com.jourgeois.backend.service.PostService;
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

    public SearchStatistics(SearchHistoryService searchHistoryService, HomeService homeService, RedisService redisService) {
        this.searchHistoryService = searchHistoryService;
        this.homeService = homeService;
        this.redisService = redisService;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final SearchHistoryService searchHistoryService;
    private final HomeService homeService;

    private final RedisService redisService;

    @Scheduled(cron = "0 */30 * * * *")
    public void getHotKeywordEveryTenMinutes() {
        // 현재 시간 마지막 집계 시간 기록
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.now().minusMinutes(30L);
        String toFormatted = to.format(DATE_TIME_FORMATTER);
        String fromFormatted = from.format(DATE_TIME_FORMATTER);
        try {
            // 집계 가져옴
            List<SearchHistoryDTO> jpa_cur_hot = this.searchHistoryService.getHotKeyword();
            List<SearchHistoryVO> cur_hot = new ArrayList<>();
            for (SearchHistoryDTO s : jpa_cur_hot) {
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
//            System.out.println("현재 로그 조회 성공");

            // 이전 기록 가져옴
            SearchTrendDto prev = redisService.getHotKeywords("cur");

//            System.out.println("이전 로그 조회 성공");

            // 이전 기록이 없으면 현재 기록을 이전 기록과 현재 기록에 등록
            if (prev == null) {
//                System.out.println("통계가 없음");
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
                redisService.setHotKeywords("cur", searchTrend);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 */12 * * *")
    public void getWeeklyHotKeyword() {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        String toFormatted = to.format(DATE_TIME_FORMATTER);
        String fromFormatted = from.format(DATE_TIME_FORMATTER);
        try {
            List<SearchHistoryDTO> jpa_cur_hot = this.searchHistoryService.getWeeklyHotKeyword();
            List<SearchHistoryVO> cur_hot = new ArrayList<>();
            for (SearchHistoryDTO s : jpa_cur_hot) {
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
//            System.out.println("현재 로그 조회 성공");
//            System.out.println(prev.getFrom());
            SearchTrendDto prev = redisService.getWeeklyHotKeywords("cur");

//            System.out.println("이전 로그 조회 성공");
            if (prev == null) {
//                System.out.println("통계가 없음");
                for (int i = 0; i < cur_hot.size(); i++)
                    searchTrend.getDelta().add(Integer.valueOf(-1));
                redisService.setWeeklyHotKeywords("prev", searchTrend);
                redisService.setWeeklyHotKeywords("cur", searchTrend);
            } else {
                this.redisService.setWeeklyHotKeywords("prev", prev);
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
                redisService.setWeeklyHotKeywords("cur", searchTrend);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}