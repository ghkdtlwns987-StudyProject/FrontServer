package com.front.studyprojectfrontserver.Domain.Common.Advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.studyprojectfrontserver.Domain.Exception.*;
import com.front.studyprojectfrontserver.Domain.Member.Dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException{
        return(response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException{
        InputStream is = response.getBody();
        String messageBody = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);
        ResponseDto<Object> exception = objectMapper.readValue(messageBody, ResponseDto.class);

        int status =response.getStatusCode().value();
        List<String> errorMessages = exception.getErrorMessages();

        if(exception.isSuccess()){
            throw new RestException(errorMessages.toString());
        }

        if (status == HttpStatus.UNAUTHORIZED.value()) {
            throw new CustomUnauthorizedException(errorMessages.toString());
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            throw new CustomNotFoundException(errorMessages.toString());
        } else if (status == HttpStatus.BAD_REQUEST.value()) {
            throw new CustomBadRequestException(errorMessages.toString());
        } else if (status == HttpStatus.METHOD_NOT_ALLOWED.value()) {
            throw new CustomMethodNotAllowedException(errorMessages.toString());
        } else if (status == HttpStatus.FORBIDDEN.value()) {
            throw new CustomForbiddenException(errorMessages.toString());
        } else if (status == HttpStatus.BAD_GATEWAY.value()) {
            throw new CustomGatewayException(errorMessages.toString());
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new CustomServerException(errorMessages.toString());
        }
    }
}
