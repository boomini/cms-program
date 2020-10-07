package com.cmslogin.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Board extends CommonDateEntity {
  private Long boardId;
  private String name;

}
