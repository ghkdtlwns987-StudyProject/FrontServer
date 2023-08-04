package com.front.studyprojectfrontserver.Domain.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFailureHandler implements AuthenticationFailureHandler {

    /**
     * JWT 인증 로직 으로 login 수행 시 인증에 실패 했을 경우 제어 하기 위한 기능 입니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception Spring Security에서 인증 실패 시 발생하는 예외 입니다.
     * @throws IOException
     * @throws ServletException
     *
     * @author : 황시준
     * @since : 1.0
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        log.info("Failure Handler called");
        response.sendRedirect("/member/loginF");
    }
}
