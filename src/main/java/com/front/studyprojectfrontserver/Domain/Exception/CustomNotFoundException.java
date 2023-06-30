package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * Not Found 에러를 잡는 예외처리
 */
public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String message) {
        super(message);
    }
}