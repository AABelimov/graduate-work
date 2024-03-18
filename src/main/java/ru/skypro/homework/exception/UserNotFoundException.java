package ru.skypro.homework.exception;

public class UserNotFoundException extends NotFoundException {

    private final Integer id;

    public UserNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "User with id = " + id + " not found!";
    }
}
