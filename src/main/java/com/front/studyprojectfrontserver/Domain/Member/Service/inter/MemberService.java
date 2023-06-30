package com.front.studyprojectfrontserver.Domain.Member.Service.inter;

import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpResponseDto;
import org.springframework.http.ResponseEntity;


public interface MemberService {
    /**
     * 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @return 회원 등록 API 호출 결과 입니다.
     * @author 황시준
     * @since 1.0
     */
    SignUpResponseDto signUp(SignUpRequestDto request);
}
