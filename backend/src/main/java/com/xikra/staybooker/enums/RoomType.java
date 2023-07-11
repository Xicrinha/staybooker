package com.xikra.staybooker.enums;

public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double"),
    TRIPLE("Triple"),
    SUITE("Suite"),
    FAMILY("Family");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
