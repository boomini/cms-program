package com.cmslogin.backend.mapper;

import java.util.List;

import com.cmslogin.backend.model.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  User selectUserById(Long id);

  List<User> selectAllUser();

  void updateUserById(User user);

  void insertUser(User user);

  void deleteUser(Long msrl);
}