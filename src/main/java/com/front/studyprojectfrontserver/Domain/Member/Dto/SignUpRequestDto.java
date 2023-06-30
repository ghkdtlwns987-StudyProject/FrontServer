package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 회원가입할 때 쓰이는 DTO입니다.
 * @author : 황시준
 * @since : 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    @Size(min = 8)
    private String pwd;

    @NotBlank
    private String phone;
}
