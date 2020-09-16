package com.cmslogin.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
  @Bean
  public Docket swaggerApi() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.cmslogin.backend.controller"))
        // controller 하단의 내용을 읽어 mapping된 resource들을 문서화시킵니다.
        .paths(PathSelectors.any())
        // pathselectors.ant("/v1/**")
        // 아래와 같이 작성시 v1으로 시작하는 resource들만 문서화시킬수도 있다.
        .build().useDefaultResponseMessages(false);

  }

  private ApiInfo swaggerInfo() {
    return new ApiInfoBuilder().title("Spring API Documentation").description("앱 개발시 사용되는 서버 API에 대한 연동 문서입니다.")
        .license("bomin").version("1").build();
  }

}
