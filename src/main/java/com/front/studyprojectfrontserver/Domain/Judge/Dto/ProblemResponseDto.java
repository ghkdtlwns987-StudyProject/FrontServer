package com.front.studyprojectfrontserver.Domain.Judge.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemResponseDto {
    private int id;
    private String title;
    private String description;
    private int time_limit;
    private int memory_limit;
    private boolean spacecheck;
    private String createUser;
    private boolean isPublic;
    private boolean isDeleted;
}

