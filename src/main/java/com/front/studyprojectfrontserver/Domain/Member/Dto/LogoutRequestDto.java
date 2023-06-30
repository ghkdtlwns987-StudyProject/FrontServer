package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.*;

/**
 * 로그아웃을 위한 Dto입니다.
 *
 * @author : 황시준
 * @since  : 1.0
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequestDto {
    private String key;
}
