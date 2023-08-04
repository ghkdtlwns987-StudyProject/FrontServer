package com.front.studyprojectfrontserver.Domain.Member.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles {
    USER("ROLE_USER", "유저"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String id;
    private final String name;
}