package com.cmslogin.backend.service;

import java.util.Optional;

import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String userPk) {
    Optional<User> user = Optional.ofNullable(userService.getUserByUid(userPk));
    return user.orElseThrow(CUserNotFoundException::new);
    // return user.orElseThrow(CUserNotFoundException::new);

  }

}
