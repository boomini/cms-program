package com.cmslogin.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable()
        // restapi이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트한다.
        .csrf().disable()
        // rest api이므로 csrf 보안이 필요없으므로 disable 처리
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // jwt token으로 인증하므로 세션은 필요없으므로 생성안함
        .and().authorizeRequests()
        // 다음리퀘스트에 대한 사용권한체크
        .antMatchers("/*/signin", "/*/signin/**", "/*/signup", "/*/signup/**", "/*/userlist", "/social/**").permitAll()
        // 가입 및 인증 주소는 누구나 접근 가능
        .antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers(HttpMethod.GET, "helloworld/**").permitAll()
        // helloworld로 시작하는 get요청 리소스는 누구나 접근 가능
        .antMatchers("/*/users").hasRole("ADMIN").anyRequest().hasAnyRole("USER, ADMIN")
        // 그외나머지 요청은 모두 인증된 회원만 접근 가능
        // 커스터마이징 하기.
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        // 토큰이 있으면 UsernamePasswordAuthenticationFilter를 건너뛴다.(아이디 패스워드를 인증한느 필터)
        .headers().frameOptions().sameOrigin();
    // jwt token 필터를 id/passsword인증 필터 전에 넣는다.
  }

  @Override // ignore check swagger resource
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
        "/swagger/**");
    // spring security 적용 후에는 모든 리소스에 대한 접근이 제한되므로 swagger 페이지에 대해서는 예외를 적용해야 페이지에
    // 접근할 수 있다.

  }

}
