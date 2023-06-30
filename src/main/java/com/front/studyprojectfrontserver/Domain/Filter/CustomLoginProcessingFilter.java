package com.front.studyprojectfrontserver.Domain.Filter;

import com.front.studyprojectfrontserver.Domain.Exception.InvalidLoginRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Form Login을 위해 UsernamePasswordAuthenticationFilter를 대체하여 custom한 filter입니다.
 *
 * @author : 황시준
 * @since  : 1.0
 */
@Slf4j
public class CustomLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public CustomLoginProcessingFilter(String processingUrl){
        super(processingUrl);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
      String loginId = request.getParameter("email");
      String password = request.getParameter("pwd");

      if(Objects.isNull(loginId) || Objects.isNull(password)){
          throw new InvalidLoginRequestException();
      }

      return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
              loginId,
              password
      ));
    }
}
