package com.cmslogin.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class Post extends CommonDateEntity {
  private int post_id;
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

  public Post setUpdate(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
    return this;
  }

}
