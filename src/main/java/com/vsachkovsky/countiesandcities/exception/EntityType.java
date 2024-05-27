package com.vsachkovsky.countiesandcities.exception;

public enum EntityType {
    CITY("City"),
    COUNTRY("Country");

    private final String message;

    EntityType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
