package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * ad Request 예외를 잡기 위한 예외
 */
public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(String message) {
        super(message);
    }
}
