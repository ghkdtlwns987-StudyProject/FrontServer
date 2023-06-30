package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * 인증 서버 오류를 잡기 위한 예외처리
 */
public class CustomServerException extends RuntimeException {

    public CustomServerException(String message) {
        super(message);
    }
}
