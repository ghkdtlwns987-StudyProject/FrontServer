package com.front.studyprojectfrontserver.Domain.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GatewayConfig {
    @Value("${spring.auth.url}")
    public String authUrl;
}
