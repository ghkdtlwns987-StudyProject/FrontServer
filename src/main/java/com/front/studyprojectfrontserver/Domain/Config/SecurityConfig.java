package com.front.studyprojectfrontserver.Domain.Config;


import com.front.studyprojectfrontserver.Domain.Common.CookieUtils;
import com.front.studyprojectfrontserver.Domain.Filter.CustomAuthenticationManager;
import com.front.studyprojectfrontserver.Domain.Filter.CustomFailureHandler;
import com.front.studyprojectfrontserver.Domain.Filter.CustomLoginProcessingFilter;
import com.front.studyprojectfrontserver.Domain.Filter.CustomLogoutHandler;
import com.front.studyprojectfrontserver.Domain.Member.Adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/member/**").permitAll()
                .mvcMatchers("/judge/**").authenticated()
                .mvcMatchers("/profile/**").authenticated()
                .mvcMatchers("/admin/**").hasRole("ADMIN");

        http.formLogin().loginPage("/member/login");
        http.addFilterAt(
                customLoginProcessingFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler())
                .logoutSuccessUrl("/member/logoutS");

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.csrf().disable();
        http.cors().disable();
        return http.build();
    }

    /**
     * Logout 시 작동하는 custom handler를 Bean으로 등록합니다.
     *
     * @return Custom 한 Logout Handler 입니다.
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler(redisTemplate, memberAdapter, cookieUtils);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager를 custom한 manager를 Bean으로 등록하기 위한 기능입니다.
     *
     * @return AuthenticationManager를 custom한 manager를 반환합니다.
     */
    @Bean
    public CustomAuthenticationManager customAuthenticationManager(){
        return new CustomAuthenticationManager(memberAdapter, redisTemplate, cookieUtils);
    }

    /**
     * UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 입니다. form login 요청 시 동작하는 filter
     * 입니다.
     *
     * @return UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 를 반환합니다.
     */
    @Bean
    public CustomLoginProcessingFilter customLoginProcessingFilter(){
        CustomLoginProcessingFilter customLoginProcessingFilter = new CustomLoginProcessingFilter("/authentication/login");
        customLoginProcessingFilter.setAuthenticationManager(customAuthenticationManager());
        customLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return customLoginProcessingFilter;
    }

    /**
     * 인증 실패 시 동작하는 Failure Handler를 @Bean으로 등록합니다.
     *
     * @return 인증 실패 시 작동하는 AuthenticationFailureHandler 입니다.
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomFailureHandler();
    }
}
