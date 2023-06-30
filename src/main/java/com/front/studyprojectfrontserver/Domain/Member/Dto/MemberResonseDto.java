package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class MemberResonseDto {
    private String userId;
    private String email;
    private String nickname;
    private String name;
    private String phone;
    private int roles;
    private Date createAt;
}
