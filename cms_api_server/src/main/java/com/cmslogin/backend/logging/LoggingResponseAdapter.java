package com.cmslogin.backend.logging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class LoggingResponseAdapter implements ResponseBodyAdvice<Object> {
  Logger log = LoggerFactory.getLogger("api-response");

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {

    if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
      logResponse(((ServletServerHttpRequest) request).getServletRequest(),
          ((ServletServerHttpResponse) response).getServletResponse(), body);
    }

    return body;
  }

  private void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      Object body) {

    HttpSession session = httpServletRequest.getSession();
    Object reqTime = session.getAttribute("requestTime");

    Map<String, Object> resMap = new HashMap<>();
    resMap.put("requestTime", reqTime);
    resMap.put("url", httpServletRequest.getRequestURL().toString());
    resMap.put("statuscode", httpServletResponse.getStatus());

    ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

    if (body != null) {
      try {
        String strBody = mapper.writeValueAsString(body);
        resMap.put("body", strBody);
      } catch (IOException e) {
        resMap.put("body", body);
      }
    } else {
      resMap.put("body", body);
    }

    log.info("<== {}", resMap);
  }

}
