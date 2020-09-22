package com.cmslogin.backend;

import java.util.List;

import com.cmslogin.backend.mapper.TestMapper;
import com.cmslogin.backend.model.User;
import com.cmslogin.backend.service.UserService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@Transactional
@MapperScan("com.cmslogin.backend")
public class BackendApplication implements CommandLineRunner {

  @Autowired
  TestMapper testMapper;

  @Autowired
  UserService service;

  public static void main(final String[] args) {
    SpringApplication.run(BackendApplication.class, args);

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  public void run(final String... args) throws Exception {
    // System.out.println("Time:" + testMapper.getTime());
    // getUserById();
    // getAllUser();
    // addUsers();
    // deleteUsers();
    // updateUsers();

  }

  public void getUserById() {
    User user = service.getUserById(1L);
    log.info("user: {}", user);
  }

  public void getAllUser() {
    List<User> users = service.getAllUser();
    log.info("users: {}", users);
  }

  public void addUsers() {
    service.addUser(new User("bomin1", "boomini"));
    service.addUser(new User("bomin2", "boomini"));
    service.addUser(new User("bomin3", "boomini"));
  }

  public void updateUsers() {
    service.modifyUserById(new User(1L, "DDD", "SSSS"));
  }
}
