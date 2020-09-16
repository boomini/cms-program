package com.cmslogin.backend.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("user")
public class User implements UserDetails {
  private long msrl;
  private String uid;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  private String name;

  public User(String uid, String name) {
    this.uid = uid;
    this.name = name;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
    auth.add(new SimpleGrantedAuthrity(AUTHORITY));
    return auth;
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return this.uid;
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
