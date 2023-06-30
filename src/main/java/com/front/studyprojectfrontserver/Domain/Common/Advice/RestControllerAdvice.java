package com.front.studyprojectfrontserver.Domain.Common.Advice;

import com.front.studyprojectfrontserver.Domain.Exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;

import java.util.List;

/**
 * RestException Advice입니다.
 * 예외처리 메시지를 웹 화면에 그대로 출력되지 않기 위해 사용합니다.
 * @author : 황시준
 * @since  : 1.0
 */

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseDto<Void> handleRestException(Exception e) {
        return ResponseDto.<Void>builder()
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .errorMessages(List.of(e.getMessage()))
                .build();
    }
}
