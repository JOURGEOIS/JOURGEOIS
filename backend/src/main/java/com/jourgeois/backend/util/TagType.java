package com.jourgeois.backend.util;

public enum TagType {

    SPECIAL("특별"),
    ALONE("혼술"),
    PARTY("파티"),
    LOVE("연인");

    private final String value;

    TagType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
