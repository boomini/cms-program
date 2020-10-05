package com.cmslogin.backend.controller;

import java.util.Optional;

import com.cmslogin.backend.service.KakaoService;
import com.cmslogin.backend.service.ResponseService;
import com.cmslogin.backend.service.UserService;
import com.cmslogin.backend.advice.exception.CEmailSigninFailedException;
import com.cmslogin.backend.advice.exception.CUserExistException;
import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.config.security.JwtTokenProvider;
import com.cmslogin.backend.config.security.PasswordEncoding;
import com.cmslogin.backend.model.KakaoProfile;
import com.cmslogin.backend.model.User;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.model.response.ListResult;
import com.cmslogin.backend.model.response.SingleResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1.sign" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {
  @Autowired
  private UserService userService;

  private final KakaoService kakaoService;

  private final JwtTokenProvider JwtTokenProvider;

  private final ResponseService responseService;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);

  // @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
  // @PostMapping(value = "/signin")
  // public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일 ", required
  // = true) @RequestParam("uid") String uid,
  // @ApiParam(value = "비밀번호", required = true) @RequestParam("password") String
  // password) {
  // User user = userService.getUserByUid(uid);
  // Optional<User> userException = Optional.ofNullable(user);
  // responseService.getSingleResult(userException.orElseThrow(CEmailSigninFailedException::new));

  // System.out.println(user);
  // System.out.println("패스워드확인" + password + user.getPassword());
  // if (!passwordEncoder.matches(password, user.getPassword()))
  // throw new CEmailSigninFailedException();

  // return responseService
  // .getSingleResult(JwtTokenProvider.createToken(String.valueOf(user.getMsrl()),
  // user.getAUTHORITY()));
  // }

  @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
  @PostMapping(value = "/signin")
  public SingleResult<String> signin(@RequestBody User adminModel) {

    User user = userService.getUserByUid(adminModel.getUid());
    Optional<User> userException = Optional.ofNullable(user);
    responseService.getSingleResult(userException.orElseThrow(CEmailSigninFailedException::new));
    if (!passwordEncoder.matches(adminModel.getPassword(), user.getPassword()))
      throw new CEmailSigninFailedException();
    return responseService
        .getSingleResult(JwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getAUTHORITY()));
  }

  @ApiOperation(value = "가입", notes = "회원가입을 한다")
  @PostMapping(value = "/signup")
  public CommonResult signup(@RequestBody User model) {

    User user = new User(model.getUid(), passwordEncoding.encode(model.getPassword()), model.getName());
    userService.addUser(user);
    return responseService.getSuccessResult();
  }
  // 패스워드 보안

  @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
  @GetMapping(value = "/userlist")
  public ListResult<User> findAllUser() {
    return responseService.getListResult(userService.getAllUser());
    // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력
  }

  @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
  @PostMapping(value = "/signin/kakao")
  public SingleResult<String> signinByProvider(
      @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {
    KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
    kakaoService.getKakaoProfile(accessToken);
    User user = new User();
    user = userService.getUserByUidAndProvider(String.valueOf(profile.getId()), "kakao");
    if (user == null)
      throw new CUserNotFoundException();

    // .orElseThrow(CUserNotFoundException::new);
    return responseService
        .getSingleResult(JwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getAUTHORITY()));
  }

  @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
  @PostMapping(value = "/signup/{provider}")
  public CommonResult signupProvider(
      @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
      @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
      @ApiParam(value = "이름", required = true) @RequestParam String name) {

    KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
    User user = userService.getUserByUidAndProvider(String.valueOf(profile.getId()), provider);

    Optional<User> userException = Optional.ofNullable(user);
    if (userException.isPresent())
      throw new CUserExistException();
    user = new User();
    user.setUid(String.valueOf(profile.getId()));
    user.setName(name);
    user.setProvider(provider);
    ;
    System.out.println("user정보" + user);
    userService.addSocialUser(user);

    return responseService.getSuccessResult();
  }

}