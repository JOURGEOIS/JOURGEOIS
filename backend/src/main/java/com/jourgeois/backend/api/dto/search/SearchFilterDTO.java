package com.jourgeois.backend.api.dto.search;

import java.util.Arrays;
import java.util.List;

public class SearchFilterDTO {
    private int type;
    private int[] abv;
    private List<Integer> materials;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getAbv() {
        return abv;
    }

    public void setAbv(int[] abv) {
        this.abv = abv;
    }

    public List<Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Integer> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "SearchFilterDto{" +
                "type=" + type +
                ", abv=" + Arrays.toString(abv) +
                ", materials=" + materials +
                '}';
    }
}
