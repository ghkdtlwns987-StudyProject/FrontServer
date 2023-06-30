package com.front.studyprojectfrontserver.Domain.Exception;

/**
 * 로그인 요청 시 잘못된 값을 입력했을 때 발생하는 에러입니다.
 * @author : 황시준
 * @since  : 1.0
 */
public class InvalidLoginRequestException extends RuntimeException{
    private static final String MESSAGE = "INVALID LOGIN ID, PASSWORD";

    public InvalidLoginRequestException(){
        super(MESSAGE);
    }
}
