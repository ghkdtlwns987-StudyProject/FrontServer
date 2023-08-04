package com.front.studyprojectfrontserver.Domain.Judge.Dto;

import lombok.*;

/**
 * 회원이 속한 수업에 대한 응답을 가져오는 Dto입니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ClassResponseDto {
    private Long classId;       // 과목 Id
    private String className;   // 과목명
    private Long subjectId;     // 주제
    private int isPublic;       // 이용가능 여부
    private int available;      // 접근 가능 여부
    private int show_up;        // 과목 공개 여부
}
