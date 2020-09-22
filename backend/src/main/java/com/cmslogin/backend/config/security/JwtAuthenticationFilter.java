package com.cmslogin.backend.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {
  // Jwt가 유효한 토큰인지 인증하기 위한 Filter

  private JwtTokenProvider jwtTokenProvider;

  // Jwt Provider 주입
  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  // Request로 들어오는 Jwt Token의 유효성을 검증 (jwtTokenProvider.validateToken)하는 filter를
  // filterChain에 등록합니다.
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      Authentication auth = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
      // 인증정보를 holding setting 해놓는것
    }
    // 헤더에서 토큰을 가져와서 유효한지 체크하고 사용자 정보를 가져온 후 securitycontextholder에 세팅해놓겠따.

    chain.doFilter(request, response);
  }

}
