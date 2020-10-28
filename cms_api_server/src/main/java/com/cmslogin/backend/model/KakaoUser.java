package com.cmslogin.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KakaoUser {
  String token;
  String name;
  String password;

}
