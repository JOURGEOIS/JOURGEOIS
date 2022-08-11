package com.jourgeois.backend.util;

public enum NotificationType {

    FOLLOW("follow"),
    LIKE("like"),
    COMMENT("comment");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
