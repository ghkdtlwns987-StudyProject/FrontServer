package com.front.studyprojectfrontserver.Domain.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GatewayConfig {
    @Value("${third.url}")
    public String thirdUrl;

    @Value("${auth.url}")
    public String authUrl;
}
