package com.cmslogin.backend.logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class LoggingRequestFilter extends GenericFilterBean {
  Logger log = LoggerFactory.getLogger("api-request");

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest currentRequest = (HttpServletRequest) request;

    long reqTime = System.currentTimeMillis();
    HttpSession session = currentRequest.getSession();
    session.setAttribute("requestTime", reqTime);

    logRequest(currentRequest);

    chain.doFilter(currentRequest, response);

  }

  private void logRequest(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession();
    Object reqTime = session.getAttribute("requestTime");

    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put("requestTime", reqTime);
    requestMap.put("url", httpServletRequest.getRequestURL().toString());
    requestMap.put("queryString", httpServletRequest.getQueryString());
    requestMap.put("method", httpServletRequest.getMethod());
    // requestMap.put("remoteAddr", ip);

    log.info("==> {}", requestMap);
  }
}
