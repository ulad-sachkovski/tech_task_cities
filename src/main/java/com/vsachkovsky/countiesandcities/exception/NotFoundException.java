package com.vsachkovsky.countiesandcities.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException notFoundByName(EntityType type, String city) {
        String message = String.format("%s %s was not found", type.getMessage(), city);
        return new NotFoundException(message);
    }

    public static NotFoundException notFoundById(EntityType type, String id) {
        String message = String.format("%s with id %s was not found", type.getMessage(), id);
        return new NotFoundException(message);
    }
}
