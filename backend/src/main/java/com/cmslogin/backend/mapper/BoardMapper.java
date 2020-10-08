package com.cmslogin.backend.mapper;

import java.util.List;

import com.cmslogin.backend.model.Board;
import com.cmslogin.backend.model.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface BoardMapper {
  Board selectBoardByName(String name);

  List<Post> selectPostByBoard(Board board);

  Post findById(int post_id);

  @Options(useGeneratedKeys = true, keyProperty = "post_id")
  void save(Post post);

  void delete(int post_id);
}
