package ru.skypro.homework.exception;

public class CommentNotFoundException extends NotFoundException {

    private final Integer id;

    public CommentNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Comments with id = " + id + " not found!";
    }
}
