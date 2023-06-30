package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * Http Header에 들어있는 정보를 파싱하고자 할 때, 유효하지 않은 경우 발생하는 예외입니다.
 * @author : 황시준
 * @since  : 1.0
 */
public class InvalidHttpHeaderException extends RuntimeException {

    public InvalidHttpHeaderException(String message) {
        super(message);
    }
}

