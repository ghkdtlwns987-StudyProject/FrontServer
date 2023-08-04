package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberResonseDto {
    private String userId;
    private String email;
    private String nickname;
    private String name;
    private String phone;
    private List<String> roles;
    private Date createAt;
}
