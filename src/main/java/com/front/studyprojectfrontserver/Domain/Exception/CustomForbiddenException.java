package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * Forbidden 에러를 잡음
 */
public class CustomForbiddenException extends RuntimeException {

    public CustomForbiddenException(String message) {
        super(message);
    }
}