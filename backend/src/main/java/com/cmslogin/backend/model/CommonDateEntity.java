package com.cmslogin.backend.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class CommonDateEntity {
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

}
