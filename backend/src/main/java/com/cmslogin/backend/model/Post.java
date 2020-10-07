package com.cmslogin.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Post extends CommonDateEntity {
  private Long postId;
  private String author;
  private String title;
  private String content;

  private Board board;

  private User user;

  public Post(User user, Board board, String author, String title, String content) {
    this.user = user;
    this.board = board;
    this.author = author;
    this.title = title;
    this.content = content;
  }

  // 수정시 데이터 처리
  public Post setUpdate(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
    return this;
  }

}
