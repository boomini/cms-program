package com.cmslogin.backend.model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;

@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("user")
public class User extends CommonDateEntity implements UserDetails {
  private long msrl;
  private String uid;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String name;
  // private List<String> AUTHORITY = new ArrayList<>();
  private String AUTHORITY;
  private String provider;

  public User(String uid, String name) {
    this.uid = uid;
    this.name = name;
  }

  public User(long msrl, String uid, String name) {
    this.msrl = msrl;
    this.uid = uid;
    this.name = name;
  }

  public User(String uid, String password, String name) {
    this.uid = uid;
    this.password = password;
    this.name = name;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
    auth.add(new SimpleGrantedAuthority(this.AUTHORITY));
    // return
    // this.AUTHORITY.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return auth;
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return this.uid;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }
}
