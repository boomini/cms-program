package com.cmslogin.backend.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class CommonDateEntity {
  private LocalDateTime create_at;
  private LocalDateTime modified_at;

}
