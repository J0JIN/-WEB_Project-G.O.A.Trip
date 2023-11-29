package com.ssafy.goatrip.controller;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.goatrip.model.dto.BoardDTO;
import com.ssafy.goatrip.model.dto.BoardListDto;
import com.ssafy.goatrip.model.service.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	@Autowired
	private BoardService bservice;

	// 글 동록
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping("/register")
	public ResponseEntity<?> createBoard(@RequestBody BoardDTO board) {
		System.out.println("글 받아오기 :" + board);
		int result;
		try {
			result = bservice.createArticle(board);
			String msg = "";
			msg = "게시글 등록에 성공했습니다.";
			return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}

	}

	// 글 삭제
	@DeleteMapping("/{article_id}")
	public ResponseEntity<?> deleteByIdBoard(@PathVariable("article_id") String article_id) throws SQLException {
		int result;
		try {
			result = bservice.deleteByIdArticle(article_id);
			String msg = "";
			msg = "게시글 삭제에 성공했습니다.";
			System.out.println(article_id + "번 글 삭제 성공");
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}

	// 글 수정
	@PutMapping("/modify")
	public ResponseEntity<?> modifyByIdBoard(@RequestBody BoardDTO board) throws SQLException {
		System.out.println(board.getArticle_id()+"번 글 수정하려고 옴 ");
		System.out.println("수정 하려는 값은 : "+board);
		
		int result;
		try {
			result = bservice.modifyArticle(board);
			String msg = "";
			msg = "게시글 수정에 성공했습니다.";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/modify/{article_id}")
	public ResponseEntity<?> getmodifyByIdBoard(@PathVariable("article_id") int article_id){
		System.out.println("이건 뭔데?");
		try {
			HttpStatus status = HttpStatus.OK;
			BoardDTO board = bservice.selectByIdArticle(article_id);
			return new ResponseEntity<BoardDTO>(board,status);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
		
	}

	// 글 아이디로 검색
	@GetMapping("/{article_id}")
	public ResponseEntity<?> selectByIdBoard(@PathVariable("article_id") int article_id) throws SQLException {
		System.out.println(article_id + "번 글 얻으러 왔다");
		BoardDTO article = bservice.selectByIdArticle(article_id);

		return new ResponseEntity<BoardDTO>(article, HttpStatus.OK);
	}

	// 글 카테고리로 검색
	// 공지사항 : 1 , qna : 2 , 자유게시판 : 3
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/list")
	public ResponseEntity<?> selectByCategoryBoard(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map)
			throws SQLException {
		log.info("listArticle map - {}", map);

		try {
			BoardListDto boardListDto = bservice.selectByCategoryBoard(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	// 댓글 검색
	// 댓글 삭제
	// 댓글 수정
	// 댓글 검색
}
