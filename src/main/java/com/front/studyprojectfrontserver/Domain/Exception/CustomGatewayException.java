package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * Gateway 오류를 잡기 위한 예외처리
 */
public class CustomGatewayException extends RuntimeException {

    public CustomGatewayException(String message) {
        super(message);
    }
}
