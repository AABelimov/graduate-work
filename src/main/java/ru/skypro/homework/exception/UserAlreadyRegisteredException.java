package ru.skypro.homework.exception;

public class UserAlreadyRegisteredException extends BadRequestException {

    private final String username;

    public UserAlreadyRegisteredException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User with username - " + username + " already registered";
    }
}
