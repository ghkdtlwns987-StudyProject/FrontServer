package com.front.studyprojectfrontserver.Domain.Judge.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProblemRequestDto {
    private String title;
    private String description;
    private int time_limit;
    private int memory_limit;
    private boolean spacecheck;
    private String createUser;
}
