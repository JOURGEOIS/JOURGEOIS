package com.jourgeois.backend.util;

public enum ImgType {

    PROFILE("profile"),
    POST("post");

    private final String value;

    ImgType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
