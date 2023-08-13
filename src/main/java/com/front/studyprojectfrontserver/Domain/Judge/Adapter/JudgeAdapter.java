package com.front.studyprojectfrontserver.Domain.Judge.Adapter;

import com.front.studyprojectfrontserver.Domain.Config.GatewayConfig;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ProblemRequestDto;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ProblemResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Component
@RequiredArgsConstructor
public class JudgeAdapter {
    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;
    public ProblemResponseDto createProblem(ProblemRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProblemRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<ProblemResponseDto>> response = restTemplate.exchange(
                gatewayConfig.memberUrl + "/createProblem",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response={}", response.getBody().getData());
        return response.getBody().getData();
    }
}
