package com.cmslogin.backend.controller;

import java.util.List;
import java.util.Optional;

import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.config.security.PasswordEncoding;
import com.cmslogin.backend.model.User;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.model.response.ListResult;
import com.cmslogin.backend.model.response.SingleResult;
import com.cmslogin.backend.service.ResponseService;
import com.cmslogin.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1.USER" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
  @Autowired
  private UserService userService;

  private final ResponseService responseService; // 결과를 처리함.

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);

  @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth-token", value = "로그인 성공 후 access-token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
  @GetMapping(value = "/users")
  public ListResult<User> findAllUser() {
    return responseService.getListResult(userService.getAllUser());
    // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력
  }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth-token", value = "로그인 성공 후 access-token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
  @GetMapping(value = "/user")
  public SingleResult<User> findUserByUId() {

    // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String uid = authentication.getName();
    // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
    Optional<User> user = Optional.ofNullable(userService.getUserByUid(uid));
    return responseService.getSingleResult(user.orElseThrow(CUserNotFoundException::new));
  }

  // @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
  // @GetMapping(value = "/user")
  // public SingleResult<User> findUserByUId(@RequestHeader("x-auth-token") String
  // auth) {
  // System.out.println(auth);
  // // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
  // Authentication authentication =
  // SecurityContextHolder.getContext().getAuthentication();
  // String uid = authentication.getName();
  // // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
  // Optional<User> user = Optional.ofNullable(userService.getUserByUid(uid));
  // return
  // responseService.getSingleResult(user.orElseThrow(CUserNotFoundException::new));
  // }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth-token", value = "로그인 성공 후 access-token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다.")
  @PutMapping(value = "/user")
  public CommonResult modify(@RequestBody User bodyUser) {
    // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String uid = authentication.getName();
    System.out.println(bodyUser);
    User user = new User(uid, passwordEncoding.encode(bodyUser.getPassword()), bodyUser.getName());
    userService.modifyUserById(user);
    return responseService.getSuccessResult();
  }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "x-auth-token", value = "로그인 성공 후 access-token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다.")
  @DeleteMapping(value = "/user/{uid}")
  public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable String uid) {
    userService.deleteUser(uid);
    return responseService.getSuccessResult();
  }

}
