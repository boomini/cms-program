package com.cmslogin.backend.controller;

import java.util.List;
import java.util.Optional;

import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.model.User;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.model.response.ListResult;
import com.cmslogin.backend.model.response.SingleResult;
import com.cmslogin.backend.service.ResponseService;
import com.cmslogin.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
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

  @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다")
  @GetMapping(value = "/user")
  public ListResult<User> findAllUser() {
    return responseService.getListResult(userService.getAllUser());
    // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력
  }

  @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
  @GetMapping(value = "/user/{msrl}")
  public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable long msrl) {
    Optional<User> user = Optional.ofNullable(userService.getUserById(msrl));
    return responseService.getSingleResult(user.orElseThrow(CUserNotFoundException::new));
  }

  @ApiOperation(value = "회원 추가", notes = "새로운 회원을 입력한다.")
  @PostMapping(value = "/user")
  public SingleResult<User> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
      @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
    User user = new User(uid, name);
    userService.addUser(user);
    return responseService.getSingleResult(user);
  }

  @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다.")
  @PutMapping(value = "/user")
  public SingleResult<User> modify(@ApiParam(value = "회원번호", required = true) @RequestParam Long msrl,
      @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
      @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
    User user = new User(msrl, uid, name);
    userService.modifyUserById(user);
    return responseService.getSingleResult(user);
  }

  @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다.")
  @DeleteMapping(value = "/user/{msrl}")
  public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
    userService.deleteUser(msrl);
    return responseService.getSuccessResult();
  }

}
