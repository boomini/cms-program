package com.cmslogin.backend.advice.exception;

public class CUserSignupFailedException extends RuntimeException {
  public CUserSignupFailedException(String msg, Throwable t) {
    super(msg, t);
  }

  public CUserSignupFailedException(String msg) {
    super(msg);
  }

  public CUserSignupFailedException() {
    super();
  }
}
