package com.cmslogin.backend.controller.common;

import com.cmslogin.backend.service.KakaoService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

  private final Environment env;
  private final RestTemplate restTemplate;
  private final Gson gson;
  private final KakaoService kakaoService;

  @Value("${spring.url.base}")
  private String baseUrl;

  @Value("${spring.social.kakao.client_id}")
  private String kakaoClientId;

  @Value("${spring.social.kakao.redirect}")
  private String kakaoRedirect;

  /**
   * 카카오 로그인 페이지
   */
  @GetMapping
  public ModelAndView socialLogin(ModelAndView mav) {

    StringBuilder loginUrl = new StringBuilder().append(env.getProperty("spring.social.kakao.url.login"))
        .append("?client_id=").append(kakaoClientId).append("&response_type=code").append(baseUrl)
        .append("&redirect_uri=").append(kakaoRedirect);

    mav.addObject("loginUrl", loginUrl);
    mav.setViewName("social/login");
    return mav;
  }

  /**
   * 카카오 인증 완료 후 리다이렉트 화면
   */
  @GetMapping(value = "/kakao")
  public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) {
    mav.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
    mav.setViewName("social/redirectKakao");
    return mav;
  }
}
// authorization 코드받기
// https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code
//
// access token
//
// curl -v -X POST https://kauth.kakao.com/oauth/token \
// -d 'grant_type=authorization_code' \
// -d 'client_id={REST_API_KEY}' \
// -d 'redirect_uri={REDIRECT_URI}' \
// -d 'code={AUTHORIZE_CODE}'

// response
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
// {
// "token_type":"bearer",
// "access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
/// "expires_in":43199,
// "refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
// "refresh_token_expires_in":25184000,
// "scope":"account_email profile"
// }