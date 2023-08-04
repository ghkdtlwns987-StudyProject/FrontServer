package com.front.studyprojectfrontserver.Domain.Judge.Dto;

import lombok.*;

/**
 * 현재 회원이 속한 분반을 불러오기 위한 요청을 담당하는 Dto 입니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassRequestDto {
    private String userId;
    private String email;
}
