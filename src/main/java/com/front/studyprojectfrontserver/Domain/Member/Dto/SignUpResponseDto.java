package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private String userId;
    private String email;
    private String nickname;
    private String name;

}
