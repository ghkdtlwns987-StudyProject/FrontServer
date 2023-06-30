package com.front.studyprojectfrontserver.Domain.Common.Advice;

import com.front.studyprojectfrontserver.Domain.Common.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

/**
 * 예외 처리를 위한 Controller Advice입니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {
    private final CookieUtils cookieUtils;

    @ExceptionHandler(BadCredentialsException.class)
    public String handleForbiddenException(Exception ex, Model model){
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/forbidden";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model){
        log.error("[Exception] : " , ex);
        model.addAttribute("error", ex.getMessage());
        return "common/errors/error";
    }
}
