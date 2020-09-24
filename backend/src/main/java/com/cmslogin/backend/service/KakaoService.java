package com.cmslogin.backend.service;

import com.cmslogin.backend.advice.exception.CCommunicationException;
import com.cmslogin.backend.model.KakaoProfile;
import com.cmslogin.backend.model.RetKakaoAuth;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoService {
  // service.social package를 생성하고 kakao api 연동 메서드들을 Service로 생성하여 사용.
  private final RestTemplate restTemplate;
  private final Environment env;
  private final Gson gson;

  @Value("${spring.url.base}")
  private String baseUrl;

  @Value("${social.kakao.client_id}")
  private String kakaoClientId;

  @Value("${social.kakao.redirect}")
  private String kakaoRedirect;

  public KakaoProfile getKakaoProfile(String accessToken) {
    // set header : Content-type: application/x-www-form-urlencoded
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set("Authorization", "Bearer " + accessToken);

    // set http entity
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
    try {
      // Request Profile
      ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("social.kakao.url.profile"), request,
          String.class);
      if (response.getStatusCode() == HttpStatus.OK)
        return gson.fromJson(response.getBody(), KakaoProfile.class);
    } catch (Exception e) {
      throw new CCommunicationException();
    }
    throw new CCommunicationException();
  }

  public RetKakaoAuth getKakaoTokenInfor(String code) {
    // set header : content-type : application/x-www-form-urlencoded
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    // Set parameter
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    params.add("grant_type", "authorization_code");
    params.add("client_id", kakaoClientId);
    params.add("redirect_uri", baseUrl + kakaoRedirect);
    params.add("code", code);

    // set Http entity
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("social.kakao.url.token"), request,
        String.class);

    if (response.getStatusCode() == HttpStatus.OK) {
      return gson.fromJson(response.getBody(), RetKakaoAuth.class);
    }
    return null;
  }

}
