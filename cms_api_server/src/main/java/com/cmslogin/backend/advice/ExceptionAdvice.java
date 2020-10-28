package com.cmslogin.backend.advice;

import javax.servlet.http.HttpServletRequest;

import com.cmslogin.backend.advice.exception.CAuthenticationEntryPointException;
import com.cmslogin.backend.advice.exception.CCommunicationException;
import com.cmslogin.backend.advice.exception.CEmailSigninFailedException;
import com.cmslogin.backend.advice.exception.CNotOwnerException;
import com.cmslogin.backend.advice.exception.CResourceNotExistException;
import com.cmslogin.backend.advice.exception.CUserExistException;
import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.advice.exception.CUserSignupFailedException;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.service.ResponseService;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
  // Exception이 발생할 경우 공통으로 처리하는 방법
  // ControllerAdvice annotation을 사용하면 Controller에서 발생하는 Exception을 한군데서 처리할 수 있다.
  // 예외발생시 json형태로 결과를 반환하려면 @RestControllerAdvice를 클래스에 선언하면 된다.
  private final ResponseService responseService;

  private final MessageSource messageSource;

  // @ExceptionHandler(Exception.class)
  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  // protected CommonResult defaultException(HttpServletRequest request, Exception
  // e) {
  // return
  // responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")),
  // getMessage("unKnown.msg"));
  // }

  @ExceptionHandler(CUserNotFoundException.class)
  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")),
        getMessage("userNotFound.msg"));
  }

  @ExceptionHandler(CEmailSigninFailedException.class)
  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")),
        getMessage("emailSigninFailed.msg"));

  }

  @ExceptionHandler(CAuthenticationEntryPointException.class)
  public CommonResult authenticationEntryPointException(HttpServletRequest request,
      CAuthenticationEntryPointException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")),
        getMessage("entryPointException.msg"));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")),
        getMessage("accessDenied.msg"));
  }

  @ExceptionHandler(CUserSignupFailedException.class)
  public CommonResult usersignupfailedexception(HttpServletRequest request, CUserSignupFailedException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("sighUpFailed.code")),
        getMessage("sighUpFailed.msg"));
  }

  @ExceptionHandler(CCommunicationException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public CommonResult communicationException(HttpServletRequest rquest, CCommunicationException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")),
        getMessage("communicationError.msg"));
  }

  @ExceptionHandler(CUserExistException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public CommonResult communicationException(HttpServletRequest request, CUserExistException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")),
        getMessage("existingUser.msg"));
  }

  // @ExceptionHandelr(Exception.class)
  // Exception이 발생하면 해당 Handler로 처리하겠다고 명시하는 annotation.
  // 괄호안에는 어던 exception이 발생할 때 handler를 적용할 것인지 Exception class를 인자로 넣습니다.
  // exception.class는 최상위 예외처리 객체이므로 다른 ExceptionHandler에서 걸러지지 않은 예외가 있으면 최종적으로 이
  // handler를 거치게 됩니다.

  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  // 해당 Exception이 발생하면 Response에 출력되는 HttpStatus Code가 500으로 내려가도록 설정합니다.
  // 성공시엔 200으로 내려간다.
  // 실습에서 HttpStatus Code의 역할은 성공이냐(200) 아니냐 정도의 의미만 있고 실제 사용하는 성공 실패 여부는 json으로
  // 출력되는 정보를 이용합니다.

  // code정보에 해당하는 메시지를 조회합니다.
  private String getMessage(String code) {
    return getMessage(code, null);
  }

  // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
  private String getMessage(String code, Object[] args) {
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());

  }

  @ExceptionHandler(CNotOwnerException.class)
  @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
  public CommonResult notOwnerException(HttpServletRequest request, CNotOwnerException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("notOwner.code")), getMessage("notOwner.msg"));
  }

  @ExceptionHandler(CResourceNotExistException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public CommonResult resourceNotExistException(HttpServletRequest request, CResourceNotExistException e) {
    return responseService.getFailResult(Integer.valueOf(getMessage("resourceNotExist.code")),
        getMessage("resourceNotExist.msg"));
  }

}