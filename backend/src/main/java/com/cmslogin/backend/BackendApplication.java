package com.cmslogin.backend;

import com.cmslogin.backend.mapper.TestMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cmslogin.backend.mapper")
public class BackendApplication implements CommandLineRunner {

  @Autowired
  TestMapper testMapper;

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Time:" + testMapper.getTime());

  }

}
