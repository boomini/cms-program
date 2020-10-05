package com.cmslogin.backend.service;

import java.util.List;

import com.cmslogin.backend.mapper.UserMapper;
import com.cmslogin.backend.model.User;

import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class UserService {

  @Autowired
  UserMapper userMapper;

  public User getUserById(Long id) {
    return userMapper.selectUserById(id);
  }

  public User getUserByUid(String uid) {
    return userMapper.selectUserByUid(uid);
  }

  public User getUserByUidAndProvider(String uid, String provider) {
    return userMapper.selectUserByUidAndProvider(uid, provider);
  }

  public List<User> getAllUser() {
    return userMapper.selectAllUser();
  }

  public void modifyUserById(User user) {
    userMapper.updateUserById(user);
  }

  @Options(useGeneratedKeys = true, keyProperty = "msrl")
  public void addUser(User user) {
    userMapper.insertUser(user);
  }

  public void addSocialUser(User user) {
    userMapper.inserKakaoUser(user);
  }

  public void deleteUser(String uid) {
    userMapper.deleteUser(uid);
  }

}
