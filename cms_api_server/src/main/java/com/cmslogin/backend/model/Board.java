package com.cmslogin.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class Board extends CommonDateEntity {
  private int board_id;
  private String name;

}
