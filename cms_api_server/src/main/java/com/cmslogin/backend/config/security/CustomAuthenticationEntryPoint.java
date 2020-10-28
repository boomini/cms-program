package com.cmslogin.backend.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  // spring security는 spring앞단에서 필터링을 하기 때문에 해당 상황의 excetpion이 Spring
  // DispatcherServlet까지 도달하지 않는다.
  // 온전한 Jwt가 전달이 안될 경우는 토큰 인증 처리 자체가 불가능하기 때문에, 토큰 검증 단에서 프로세스가 끝나버리게 된다.
  // 해당 예외를 잡아내려면 Spring security에서 제공하는 AuthenticationEntryPoint를 상속받아 재정의 해야 한다.
  // 예외가 발생할 경우 /exception/entrypoint로 포워딩하도록 처리

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
      throws IOException, ServletException {
    response.sendRedirect("/exception/entrypoint");
  }

}
