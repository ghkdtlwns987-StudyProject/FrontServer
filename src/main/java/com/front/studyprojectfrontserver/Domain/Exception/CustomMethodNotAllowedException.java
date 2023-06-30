package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * Method Not Allowed 예외를 잡기 위한 예외처리

 */
public class CustomMethodNotAllowedException extends RuntimeException {

    public CustomMethodNotAllowedException(String message) {
        super(message);
    }
}
