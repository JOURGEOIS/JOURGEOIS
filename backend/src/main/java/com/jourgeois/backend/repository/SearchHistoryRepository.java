package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.SearchHistoryDTO;
import com.jourgeois.backend.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    @Query(value = "select c.h_keyword as keyword, count(c.h_keyword) as hits from (select * from search_history where date_add(now(), interval -1 hour) <= h_creationtime AND h_creationtime < now() order by h_creationtime) as c group by keyword order by hits desc limit 5", nativeQuery = true)
    List<SearchHistoryDTO> getHotKeyword();

    @Query(value = "select c.h_keyword as keyword, count(c.h_keyword) as hits from (select * from search_history where date_add(now(), interval -7 day) <= h_creationtime AND h_creationtime < now() order by h_creationtime) as c group by keyword order by hits desc limit 5", nativeQuery = true)
    List<SearchHistoryDTO> getWeeklyHotKeyword();
}
