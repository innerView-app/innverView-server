package com.dev.innverview.video.domain;


public enum VideoStatus {
    PROCESSING("PROCESSING"),
    READY("READY"),
    DELETING("DELETING"),
    ERROR("ERROR");

    private final String value;

    VideoStatus(String role) {
        this.value = role;
    }

    @Override
    public String toString() {
        return value;
    }
}
