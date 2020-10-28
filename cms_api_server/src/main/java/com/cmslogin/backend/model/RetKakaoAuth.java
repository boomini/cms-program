package com.cmslogin.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetKakaoAuth {
  // kakao token api 연동시 맵핑을 위한 모델
  private String access_token;
  private String token_type;
  private String refresh_token;
  private long expires_in;
  private String scope;
}
