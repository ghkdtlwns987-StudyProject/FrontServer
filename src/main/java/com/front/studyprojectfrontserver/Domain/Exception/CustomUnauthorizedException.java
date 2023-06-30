package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * 인증서버에서 발생하는 인증 예외를 잡기 위한 Custom Exception입니다.
 *
 * @author : 황시준
 * @since  : 1.0
 */
public class CustomUnauthorizedException extends RuntimeException{
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
