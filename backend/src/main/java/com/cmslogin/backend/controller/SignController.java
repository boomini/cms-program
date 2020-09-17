package com.cmslogin.backend.controller;

import java.util.Optional;

import com.cmslogin.backend.service.ResponseService;
import com.cmslogin.backend.service.UserService;
import com.cmslogin.backend.advice.exception.CEmailSigninFailedException;
import com.cmslogin.backend.config.security.JwtTokenProvider;
import com.cmslogin.backend.model.User;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.model.response.SingleResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  private final JwtTokenProvider JwtTokenProvider;

  private final ResponseService responseService;
  private final PasswordEncoder passwordEncoder;

  @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
  @PostMapping(value = "/signin")
  public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일 ", required = true) @RequestParam String uid,
      @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
    User user = userService.getUserByUid(uid);
    Optional<User> userException = Optional.ofNullable(user);
    responseService.getSingleResult(userException.orElseThrow(CEmailSigninFailedException::new));

    System.out.println(user);
    System.out.println("패스워드확인" + password + user.getPassword());
    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new CEmailSigninFailedException();

    return responseService
        .getSingleResult(JwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getAUTHORITY()));
  }

  @ApiOperation(value = "가입", notes = "회원가입을 한다")
  @PostMapping(value = "/signup")
  public CommonResult signup(@ApiParam(value = "회원ID : 이메일 ", required = true) @RequestParam String uid,
      @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
      @ApiParam(value = "이름", required = true) @RequestParam String name) {

    User user = new User(uid, "{noop}" + password, name);
    userService.addUser(user);
    return responseService.getSuccessResult();
  }
}