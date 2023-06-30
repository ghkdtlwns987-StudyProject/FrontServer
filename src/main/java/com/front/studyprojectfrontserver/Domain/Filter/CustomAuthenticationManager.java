package com.front.studyprojectfrontserver.Domain.Filter;

import com.front.studyprojectfrontserver.Domain.Common.CookieUtils;
import com.front.studyprojectfrontserver.Domain.Config.RedisConfig;
import com.front.studyprojectfrontserver.Domain.Exception.InvalidHttpHeaderException;
import com.front.studyprojectfrontserver.Domain.Member.Adapter.MemberAdapter;
import com.front.studyprojectfrontserver.Domain.Member.Dto.LoginRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.MemberResonseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthUtil.JWT_CODE;
import static com.front.studyprojectfrontserver.Domain.Member.Jwt.AuthUtil.UUID_CODE;


/**
 * AuthenticationManager를 custom한 필터입니다.
 *
 * @author : 황시준
 * @since  : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private static final String UUID_HEADER = "UUID_HEADER";
    private static final String X_EXPIRE_HEADER = "X-Expire";

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginRequestDto loginRequestDto = new LoginRequestDto(
                (String)authentication.getPrincipal(),
                (String)authentication.getCredentials()
        );

        ResponseEntity<Void> exchange = memberAdapter.getAuthInfo(loginRequestDto);
        checkValidLoginRequest(exchange);

        String uuid = Objects.requireNonNull(exchange.getHeaders().get(UUID_HEADER).get(0));
        String expiredTime = Objects.requireNonNull(exchange.getHeaders()
                .get(X_EXPIRE_HEADER)
                .get(0));

        String accessToken = extractAuthorizationHeader(exchange);
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
        }

        ResponseEntity<ResponseDto<MemberResonseDto>> response = memberAdapter.getMemberInfo(
                loginRequestDto,
                accessToken
        );

        MemberResonseDto memberResonseDto = response.getBody().getData();

        log.info("accessToken = {}", accessToken);

        HttpServletRequest servletRequest = Objects.requireNonNull((
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse servletResponse = Objects.requireNonNull((
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes())).getResponse();

        // uuid정보를 가지고 있는 코기 입니다.
        Cookie authCookie = cookieUtils.createCookieWithoutMaxAge(UUID_CODE.getValue(), uuid);
        servletResponse.addCookie(authCookie);

        List<SimpleGrantedAuthority> authorities = getAuthorities(memberResonseDto);
        log.info("authorities={}", authorities);

        AuthInfo authInfo = new AuthInfo(
                memberResonseDto,
                accessToken,
                memberResonseDto.getRoles(),
                expiredTime
        );

        log.info("authInfo={}", authInfo);
        redisTemplate.opsForHash().put(uuid, JWT_CODE.getValue(), authInfo);

        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal().toString(),
                null,
                authorities
        );
    }

    /**
     * 회원 정보를 제공 받은 내용을 바탕으로 권한 정보를 추출하는 기능입니다.
     *
     * @param memberResponseDto 제공받은 내용 결과 정보를 담은 dto입니다.
     * @return token을 만들기 위한 권한 정보를 담은 List<SimpleGrantedAuthority>를 반환합니다.
     * @author : 황시준
     * @since  : 1.0
     */
    private List<SimpleGrantedAuthority> getAuthorities(MemberResonseDto memberResponseDto) {
        MemberResonseDto member = Objects.requireNonNull(memberResponseDto);
        log.info("member={}", member);

        String role = "USER";
        if (member.getRoles() == 1) {
            role = "ADMIN";
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return Arrays.asList(authority);
    }

    /**
     * Http Header에 담긴 정보를 헤더 정보를 파싱하여 accessToken을 추출합니다.
     * @param exchange
     * @return accessToken
     *
     * @author : 황시준
     * @since  : 1.0
     */
    private String extractAuthorizationHeader(ResponseEntity<Void> exchange) {
        String accessToken = Objects.requireNonNull(exchange.getHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0));
        if(Objects.isNull(accessToken)){
            throw new InvalidHttpHeaderException("Authorization Header is Empty");
        }

        return accessToken;
    }
    /**
     * login 요청 시 올바른 결과인지 판별하기 위해 Response Header를 검증합니다.
     * 예외 발생 시 CustomFailureHandler가 동작합니다.
     * @param exchange
     * @author : 황시준
     * @since  : 1.0
     */
    private void checkValidLoginRequest(ResponseEntity<Void> exchange){
        log.info("Auth Server Exchange check = {}", exchange);
        if(!exchange.getHeaders().containsKey(UUID_HEADER) || !exchange.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
            throw new BadCredentialsException("자격 증명 실패");
        }
    }
}
