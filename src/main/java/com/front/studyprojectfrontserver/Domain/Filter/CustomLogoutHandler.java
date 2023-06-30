package com.front.studyprojectfrontserver.Domain.Filter;

import com.front.studyprojectfrontserver.Domain.Common.CookieUtils;
import com.front.studyprojectfrontserver.Domain.Member.Adapter.MemberAdapter;
import com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthUtil.UUID_CODE;
import static com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthUtil.JWT_CODE;


/**
 * 로그아웃 시 동작하는 핸들러입니다.
 * @author : 황시준
 * @since  : 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberAdapter memberAdapter;
    private final CookieUtils cookieUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        HttpSession session = request.getSession(false);
        if(Objects.isNull(session)){
            log.info("Member is Already Logout");
            return;
        }
        session.invalidate();

        String uuid = cookieUtils.getValueFromCookie(request.getCookies(), UUID_CODE.getValue());
        log.info("UUID = {}", uuid);
        if(Objects.isNull(uuid)){
            return;
        }

        AuthInfo authInfo = (AuthInfo) redisTemplate.opsForHash().get(uuid, JWT_CODE.getValue());
        redisTemplate.opsForHash().delete(uuid, JWT_CODE.getValue());

        Cookie authCookie = cookieUtils.createCookie(UUID_CODE.getValue(), "", 0);
        response.addCookie(authCookie);

        memberAdapter.logout(uuid, authInfo.getAccessToken());

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }
}
