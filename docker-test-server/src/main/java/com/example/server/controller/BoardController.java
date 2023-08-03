package com.example.server.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.dto.ResultDto;
import com.example.server.entity.Board;
import com.example.server.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	// 과제3
	@PostMapping("api/board")
	public ResponseEntity<?> saveBoard(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		if(principalDetails == null) {throw new Exception("로그인 되어있지 않습니다.");}
		boardService.saveBoard(board, principalDetails.getUser());
		return new ResponseEntity<>(new ResultDto("게시물 작성 성공") , HttpStatus.CREATED);
	}

	//과제 4
	@GetMapping("/api/boards")
	public ResponseEntity<?> boardMain(Optional<Integer> page) {
		Pageable pegeable = PageRequest.of(page.isPresent()? page.get():0 , 5);
		Page<Board> board = boardService.findBoardAll(pegeable);
		return new ResponseEntity<>(board , HttpStatus.OK);
	}

	//과제5
	@GetMapping("api/board/{id}")
	public ResponseEntity<?> boardDetail(@PathVariable Long id) {
		Board board = boardService.findBoard(id);
		return new ResponseEntity<>(board , HttpStatus.OK);
	}
	
	//과제6
	@PutMapping("api/board/{id}")
	public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody Board board, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		if(principalDetails == null) {throw new Exception("로그인 되어있지 않습니다.");}
		boardService.updateBoard(id, board, principalDetails.getUser());
		return new ResponseEntity<>(new ResultDto("게시물 수정 성공") , HttpStatus.OK);
	}

	//과제7
	@DeleteMapping("api/board/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long id,  @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		if(principalDetails == null) {throw new Exception("로그인 되어있지 않습니다.");}
		boardService.deleteBoard(id, principalDetails.getUser());
		return new ResponseEntity<>(new ResultDto("게시물 삭제 성공"), HttpStatus.OK);
	}

	


}
