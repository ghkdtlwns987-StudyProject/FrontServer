package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.*;

import java.util.Date;

/**
 * 회원 목록을 표시하기 위한 유저 정보를 담은 Dto입니다.
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSummaryDto {
    private String email;
    private String nickname;
    private String name;
    private String phone;

    public static UserInfoSummaryDto DisplayUserInformation(
            UserInfoSummaryDto dto,
            String display
    ){
        return UserInfoSummaryDto.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();
    }
}
