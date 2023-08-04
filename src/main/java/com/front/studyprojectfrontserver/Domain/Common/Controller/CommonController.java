package com.front.studyprojectfrontserver.Domain.Common.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;

import static org.springframework.http.HttpHeaders.COOKIE;

@Controller
@RequestMapping("/")
public class CommonController {
    @GetMapping("/")
    public String main(@CookieValue(required = false, name = COOKIE) Cookie cookie){
        return "judge/index";
    }
}
