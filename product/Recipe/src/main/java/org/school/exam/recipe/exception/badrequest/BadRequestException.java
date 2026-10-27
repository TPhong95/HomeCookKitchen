package org.school.exam.recipe.exception.badrequest;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
