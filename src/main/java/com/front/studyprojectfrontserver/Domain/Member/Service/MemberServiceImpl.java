package com.front.studyprojectfrontserver.Domain.Member.Service;

import com.front.studyprojectfrontserver.Domain.Config.GatewayConfig;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Service.inter.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;
    @Override
    public SignUpResponseDto signUp(SignUpRequestDto request) {
        //request.setPassword(passwordEncoder.encode(request.getPassword()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignUpRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<SignUpResponseDto>> response = restTemplate.exchange(
                gatewayConfig.authUrl + "/auth/signup",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response={}", response.getBody().getData());
        return response.getBody().getData();
    }
}
