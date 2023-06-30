package com.front.studyprojectfrontserver.Domain.Member.Jwt;

import com.front.studyprojectfrontserver.Domain.Member.Dto.MemberResonseDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * user 정보와 accessToken을 Redis에 저장하여 관리하기 위한 클래스입니다.
 *
 * @author : 황시준
 * @since : 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo implements Serializable {

    private String loginId;
    private String email;
    private String accessToken;
    private int authorities;
    private String expiredTime;

    public AuthInfo(
            MemberResonseDto memberResponseDto,
            String accessToken,
            int authorities,
            String expiredTime
    ) {
        this.loginId = memberResponseDto.getUserId();
        this.email = memberResponseDto.getEmail();
        this.accessToken = accessToken;
        this.authorities = authorities;
        this.expiredTime = expiredTime;
    }
}