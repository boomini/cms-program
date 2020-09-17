package com.cmslogin.backend.advice;

import javax.servlet.http.HttpServletRequest;

import com.cmslogin.backend.advice.exception.CEmailSigninFailedException;
import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
  // Exception이 발생할 경우 공통으로 처리하는 방법
  // ControllerAdvice annotation을 사용하면 Controller에서 발생하는 Exception을 한군데서 처리할 수 있다.
  private final ResponseService responseService;

  // @ExceptionHandler(Exception.class)
  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  // protected CommonResult defaultException(HttpServletRequest request, Exception
  // e) {
  // return responseService.getFailResult();
  // }

  @ExceptionHandler(CUserNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
    return responseService.getFailResult();
  }

  @ExceptionHandler(CEmailSigninFailedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
    return responseService.getFailResult();
    // Integer.valueOf(getMessage("EMAIL
    // ERROR")),getMessage("emailSigninFailed.msg")
  }

}
