package com.cmslogin.backend.controller.exception;

import com.cmslogin.backend.advice.exception.CAuthenticationEntryPointException;
import com.cmslogin.backend.model.response.CommonResult;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

  @GetMapping(value = "/entrypoint")
  public CommonResult entrypointException() {
    throw new CAuthenticationEntryPointException();
  }

  @GetMapping(value = "/accessdenied")
  public CommonResult accessdeniedException() {
    throw new AccessDeniedException("");
    // 이미 존재하는 exception이므로 커스텀 exception없이 기존 accessdeniedexception을 발생시킴
  }

}
