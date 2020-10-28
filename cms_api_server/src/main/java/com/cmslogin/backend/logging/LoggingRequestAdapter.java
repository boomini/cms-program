package com.cmslogin.backend.logging;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class LoggingRequestAdapter extends RequestBodyAdviceAdapter {

  Logger log = LoggerFactory.getLogger("api-request");

  @Autowired
  HttpServletRequest httpServletRequest;

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {

    return true;
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {

    logRequest(httpServletRequest, body);

    return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
  }

  private void logRequest(HttpServletRequest httpServletRequest, Object body) {
    HttpSession session = httpServletRequest.getSession();
    Object reqTime = session.getAttribute("requestTime");

    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put("requestTime", reqTime);
    ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

    if (body != null) {
      try {
        String strBody = mapper.writeValueAsString(body);
        requestMap.put("body", strBody);
      } catch (IOException e) {
        requestMap.put("body", body);
      }
    } else {
      requestMap.put("body", body);
    }

    log.info("==> {}", requestMap);
  }
}
