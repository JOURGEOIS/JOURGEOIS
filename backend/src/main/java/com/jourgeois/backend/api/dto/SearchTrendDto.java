package com.jourgeois.backend.api.dto;

import com.jourgeois.backend.api.dto.SearchHistoryVO;
import java.io.Serializable;
import java.util.List;

public class SearchTrendDto implements Serializable {
    String from;

    String to;

    List<SearchHistoryVO> keywords;

    List<Integer> delta;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setKeywords(List<SearchHistoryVO> keywords) {
        this.keywords = keywords;
    }

    public void setDelta(List<Integer> delta) {
        this.delta = delta;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public List<SearchHistoryVO> getKeywords() {
        return this.keywords;
    }

    public List<Integer> getDelta() {
        return this.delta;
    }
}
