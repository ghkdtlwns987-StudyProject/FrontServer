package com.front.studyprojectfrontserver.Domain.Common;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Cookie 생성, 값 조회 등을 편리하게 사용하기 위한 Util 패키지입니다
 *
 * @author : 황시준
 * @since  : 1.0
 */
@Component
public class CookieUtils {
    /**
     * Cookie를 생성하는 부분입니다.
     * @param name : 쿠키 이름
     * @param value : 쿠키 값
     * @param maxAge : 유지 시간
     * @return cookie
     * @author : 황시준
     * @since  : 1.0
     */
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    /**
     * maxAge를 정하지 않고 Cookie를 생성하는 부분입니다.
     * @param name : 쿠키 이름
     * @param value : 쿠키 값
     * @return : cookie
     * @author : 황시준
     * @since  : 1.0
     */
    public Cookie createCookieWithoutMaxAge(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    /**
     * 쿠키의 값을 가져오는 부분입니다.
     * @param cookies : 쿠키 목록
     * @param key : 쿠키를 식별하기 위한 key
     * @return cookie 값
     * @author : 황시준
     * @since  : 1.0
     */
    public String getValueFromCookie(Cookie[] cookies, String key) {
        if(Objects.isNull(cookies)){
            return null;
        }
        for(Cookie cookie : cookies){
            if(Objects.equals(key, cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }


    /**
     * 쿠키에 저장된 데이터를 지우고 redis에 저장된 정보도 지웁니다.
     *
     * @param redisTemplate redisTemplate
     * @param cookie        cookie
     * @param httpServletResponse 쿠키 삭제를 위한 servlet 응답 객체
     * @author : 황시준
     * @since  : 1.0
     */
    public void deleteCookie(
            RedisTemplate<String, Object> redisTemplate,
            Cookie cookie,
            HttpServletResponse httpServletResponse
    ){
        if(Objects.nonNull(cookie)){
            String noCookieValue = cookie.getValue();
            redisTemplate.delete(noCookieValue);

            Cookie initCookie = this.createCookie("cookie", "", 0);
            httpServletResponse.addCookie(initCookie);
        }
    }
}


