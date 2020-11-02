package com.cmslogin.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.cmslogin.backend.model.Board;
import com.cmslogin.backend.model.PageModel;
import com.cmslogin.backend.model.ParamsPost;
import com.cmslogin.backend.model.Post;
import com.cmslogin.backend.model.response.CommonResult;
import com.cmslogin.backend.model.response.ListResult;
import com.cmslogin.backend.model.response.SingleResult;
import com.cmslogin.backend.service.BoardService;
import com.cmslogin.backend.service.ResponseService;
import com.cmslogin.backend.util.PagingUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = { "3.Board" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {
  private final BoardService boardService;
  private final ResponseService responseService;

  @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회한다.")
  @GetMapping(value = "/{boardName}")
  public SingleResult<Board> boardInfo(@PathVariable String boardName) {
    return responseService.getSingleResult(boardService.findBoard(boardName));
  }

  @ApiOperation(value = "게시판 글 리스트", notes = "게시판 게시글 리스트를 조회한다.")
  @GetMapping(value = "/{boardName}/posts")
  public ListResult<Post> posts(@PathVariable String boardName,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "content", required = false) String content,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "count", required = false, defaultValue = "20") int count) {
    PageModel pageModel = PagingUtils.page(page, count);

    Map<String, Object> param = new HashMap();
    param.put("boardName", boardName);
    param.put("title", "%" + title + "%");
    param.put("content", "%" + content + "%");
    System.out.println("param 정보" + param);
    int total = boardService.boardListCnt(param);
    System.out.println("total 정보" + total);
    pageModel.setTotal(total);
    List<Post> posts = boardService.findPosts(param, pageModel);

    PagingUtils.setTotalPage(pageModel);
    return responseService.getListResult(pageModel, posts);
  }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "게시판 글 작성", notes = "게시판에 글을 작성한다.")
  @PostMapping(value = "/{boardName}")
  public CommonResult post(@PathVariable String boardName, @RequestBody ParamsPost post) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String uid = authentication.getName();
    boardService.writePost(uid, boardName, post);
    return responseService.getSuccessResult();
  }

  @ApiOperation(value = "게시판 글 상세", notes = "게시판 글 상세정보를 조회한다.")
  @GetMapping(value = "/post/{post_id}")
  public SingleResult<Post> post(@PathVariable int post_id) {
    return responseService.getSingleResult(boardService.getPost(post_id));
  }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "게시판 글 수정", notes = "게시판의 글을 수정한다.")
  @PutMapping(value = "/post/{post_id}")
  public CommonResult post(@PathVariable int post_id, @Valid @ModelAttribute ParamsPost post) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String uid = authentication.getName();
    boardService.updatePost(post_id, uid, post);
    return responseService.getSuccessResult();
  }

  @ApiImplicitParams({
      @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access token", required = true, dataType = "String", paramType = "header") })
  @ApiOperation(value = "게시판 글 삭제", notes = "게시판의 글을 삭제")
  @DeleteMapping(value = "/post/{post_id}")
  public CommonResult deletePost(@PathVariable int post_id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String uid = authentication.getName();
    boardService.deletePost(post_id, uid);
    return responseService.getSuccessResult();
  }

}
