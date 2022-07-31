package com.jourgeois.backend.api.dto;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFilterDto {
    private int type;
    private int[] abv;
    private ArrayList<Integer> materials;

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

    public ArrayList<Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Integer> materials) {
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
