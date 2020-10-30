package com.cmslogin.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PageModel {
  int total;
  int page;
  int count;
  int pageIdx;
  int limit;
  int totalPage;
}
