package com.jourgeois.backend.util;

import java.io.Serializable;

public enum NotificationType implements Serializable {

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
