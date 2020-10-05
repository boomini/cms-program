package com.cmslogin.backend.mapper;

import java.util.List;

import com.cmslogin.backend.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  User selectUserById(Long id);

  List<User> selectAllUser();

  void updateUserById(User user);

  void insertUser(User user);

  void deleteUser(String uid);

  User selectUserByUid(String uid);

  void inserKakaoUser(User user);

  User selectUserByUidAndProvider(@Param("uid") String uid, @Param("provider") String provider);
}