package com.front.studyprojectfrontserver.Domain.Member.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 예외 발생 시 이전 페이지로 리다이렉트 할 Controller입니다.
 *
 * @author : 황싲누
 * @since  : 1.0
 */
@Controller
public class ExceptinHandlingController {
    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(Objects.nonNull(errorStatusCode)){
            int statusCode = Integer.parseInt(errorStatusCode.toString());

            if (Objects.equals(statusCode, HttpStatus.FORBIDDEN.value())) {
                return new ModelAndView("common/errors/forbidden");
            } else if (Objects.equals(statusCode, HttpStatus.NOT_FOUND.value())) {
                return new ModelAndView("common/errors/notfound");
            } else if (Objects.equals(statusCode, HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                return new ModelAndView("common/errors/internal-server");
            }
        }
        return new ModelAndView("common/errors/error");

    }
}
