package com.jourgeois.backend.api.dto.search;

import java.io.Serializable;

public class SearchHistoryVO implements Serializable {
    private String keyword;

    private Long hits;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getHits() {
        return this.hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }
}
