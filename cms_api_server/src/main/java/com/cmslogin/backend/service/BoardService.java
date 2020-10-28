package com.cmslogin.backend.service;

import java.util.List;

import com.cmslogin.backend.advice.exception.CNotOwnerException;
import com.cmslogin.backend.advice.exception.CResourceNotExistException;
import com.cmslogin.backend.advice.exception.CUserNotFoundException;
import com.cmslogin.backend.mapper.BoardMapper;
import com.cmslogin.backend.mapper.UserMapper;
import com.cmslogin.backend.model.Board;
import com.cmslogin.backend.model.ParamsPost;
import com.cmslogin.backend.model.Post;
import com.cmslogin.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  @Autowired
  BoardMapper boardmapper;

  @Autowired
  UserMapper usermapper;

  // 게시판 이름으로 게시판을 조회, 없을 경우 CResourceNotExistException처리
  public Board findBoard(String boardname) {
    Board board = boardmapper.selectBoardByName(boardname);
    if (board == null) {
      throw new CResourceNotExistException();
    } else
      return board;
  }

  // 게시판 이름으로 게시물 리스트 조회
  public List<Post> findPosts(String boardName) {
    return boardmapper.selectPostByBoard(findBoard(boardName));
  }

  // 게시물ID로 게시물 단건 조회. 없을 경우 CResourceNotExistException처리
  public Post getPost(int post_id) {
    Post post = boardmapper.findById(post_id);
    if (post == null) {
      throw new CResourceNotExistException();
    } else
      return post;
  }

  // 게시물을 등록 . 게시물의 회원 UID가 조회되지 않으면 CUserNotFoundException처리
  public void writePost(String uid, String boardName, ParamsPost paramsPost) {
    Board board = findBoard(boardName);
    User user = usermapper.selectUserByUid(uid);
    if (user == null)
      throw new CUserNotFoundException();
    Post post = new Post(user, board, paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
    boardmapper.save(post);
  }

  // 게시물을 수정. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException처리
  public boolean updatePost(int post_id, String uid, ParamsPost paramsPost) {
    Post post = getPost(post_id);
    User user = post.getUser();
    System.out.println(post);
    System.out.println(
        "포스트정보" + post.getPost_id() + " " + post.getAuthor() + "  " + post.getContent() + "  " + post.getTitle());
    if (!uid.equals(user.getUid()))
      throw new CNotOwnerException();
    post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
    System.out
        .println("포스트정보" + post.getPost_id() + post.getAuthor() + "  " + post.getContent() + "  " + post.getTitle());
    boardmapper.updatePost(post);

    return true;
  }

  // 게시물 삭제. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException처리
  public boolean deletePost(int post_id, String uid) {
    Post post = getPost(post_id);
    User user = post.getUser();
    if (!uid.equals(user.getUid()))
      throw new CNotOwnerException();
    boardmapper.delete(post_id);
    return true;
  }

}
