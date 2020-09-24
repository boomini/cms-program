package com.cmslogin.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
  // kakao 유저정보를 담을 객체
  private Long id;
  private Properties properties;

  @Getter
  @Setter
  @ToString
  private static class Properties {
    private String nickname;
    private String thumbnail_image;
    private String profile_image;
  }

}
