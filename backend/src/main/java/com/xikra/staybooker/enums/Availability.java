package com.xikra.staybooker.enums;

public enum Availability {
    AVAILABLE("Available"),
    BUSY("Busy"),
    MAINTENANCE("Under maintenance"),
    RESERVED("Reserved"),
    UNAVAILABLE("Unavailable");

    private String description;

    Availability(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
