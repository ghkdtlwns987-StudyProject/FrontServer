package com.front.studyprojectfrontserver.Domain.Member.Adapter;

import com.front.studyprojectfrontserver.Domain.Config.GatewayConfig;
import com.front.studyprojectfrontserver.Domain.Member.Dto.LoginRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.LogoutRequestDto;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdapter {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * 로그인 과정 중, Auth 서버에서 인증된 JWT형식의 accessToken과 UUID를 받아오는 기능입니다.
     * 해당 정보들을 Http Response Header에 담겨 반환 됩니다.
     *
     *
     * @param loginRequestDto 회원이 로그인 시 입력한 정보를 담은 DTO입니다.
     * @return Auth 서버에서 발급받은 JWT형식의 accessToken입니다.
     * @author : 황시준
     * @since  : 1.0
     */
    public ResponseEntity<Void> getAuthInfo(LoginRequestDto loginRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(loginRequestDto, headers);

        return restTemplate.exchange(
            gatewayConfig.getAuthUrl() + "/auth/login",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    /**
     * Member의 정보를 가져옵니다.
     * 정보를 가져올 때 인증 서버의 /auth/members/{loginId} 를 통해 가져오게 됩니다.
     * @param loginRequestDto 로그인 정보를 담은 dto 입니다.
     * @return MemberResponseDto
     */
    public ResponseEntity<ResponseDto<MemberResonseDto>> getMemberInfo(
            LoginRequestDto loginRequestDto
    ){
        HttpHeaders httpHeaders = new HttpHeaders();

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.thirdUrl)
                .path("/v1/members/{loginId}")
                .encode()
                .build()
                .expand(loginRequestDto.getLoginId())
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>(){
                }
        );
    }

    /**
     * 인증 서버에 logout 요청을 하기 위한 기능입니다.
     * 인증 서버는 logout 요청을 받게 되면 전달받은 uuid를 기반으로 redis에 저장된 데이터를 삭제합니다.
     * @param uuid
     * @param accessToken
     *
     * @author : 황시준
     * @since  : 1.0
     */
    public void logout(String uuid, String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);
        LogoutRequestDto logoutRequestDto = new LogoutRequestDto(uuid);

        log.info("Logout Request = {}", logoutRequestDto);
        HttpEntity<LogoutRequestDto> entity = new HttpEntity<>(logoutRequestDto, httpHeaders);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getAuthUrl())
                .path("/auth/logout")
                .encode()
                .build()
                .toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    /**
     * Auth 서버에 토큰 재발급 요청을 하기 위한 기능입니다.
     * @param uuid
     * @return
     */
    public ResponseEntity<Void> tokenReissue(String uuid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("UUID", uuid);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getAuthUrl())
                .path("/auth/reissue")
                .encode()
                .build()
                .toUri();

        HttpEntity entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}
