package com.front.studyprojectfrontserver.Domain.Judge.Adapter;

import com.front.studyprojectfrontserver.Domain.Config.GatewayConfig;
import com.front.studyprojectfrontserver.Domain.Config.RedisConfig;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ClassRequestDto;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ClassResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.LoginRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.MemberResonseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Class 정보를 불러오는데 사용되는 Adapter입니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClassAdapter {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * 회원이 속한 분반을 불러옵니다.
     * @return
     */
    public ResponseEntity<ClassResponseDto> getClassInfo(String loginId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getMemberUrl())
                .path("/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
