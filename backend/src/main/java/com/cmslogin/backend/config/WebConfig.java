package com.cmslogin.backend.config;

import com.cmslogin.backend.config.security.SecurityConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
        .allowedOrigins("http://localhost:8081", "http://localhost:8080", "http://localhost:8080/signin")
        .exposedHeaders("x-auth-token");
  }

}
