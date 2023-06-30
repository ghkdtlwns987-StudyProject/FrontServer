package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 로그인할 때 쓰이는 DTO입니다.
 * @author : 황시준
 * @since : 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    @Email
    private String loginId;

    @NotBlank
    @Size(min = 8)
    private String pwd;
}
