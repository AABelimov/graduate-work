package ru.skypro.homework.exception;

public class InvalidValueException extends BadRequestException {

    private final String message;

    public InvalidValueException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
