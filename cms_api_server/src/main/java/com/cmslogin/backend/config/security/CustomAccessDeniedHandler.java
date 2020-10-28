package com.cmslogin.backend.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  // jwt토큰은 정상인데 토큰이 가지지 못한 권한의 리소스를 접근할 때 발생하는 오류.
  // springsecurity에서 제공하는 accessdenieshandler를 상속받아 커스터 마이징 해야한다.
  // 예외가 발생할 경우 handler에서는 /exception/accessdenied로 포워딩되도록 하였다.
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
      throws IOException, ServletException {
    response.sendRedirect("/exception/accessdenied");
  }

}
