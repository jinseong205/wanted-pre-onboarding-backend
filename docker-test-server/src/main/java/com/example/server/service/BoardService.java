package com.example.server.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.entity.Board;
import com.example.server.entity.User;
import com.example.server.repository.BoardRepository;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	
	@Transactional
	public void saveBoard(Board board, User user) { // title, content
		boardRepository.save(board);
	}

	
	@Transactional(readOnly = true)
	public Page<Board> findBoardAll(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board findBoard(Long id) {
		return boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다."));
	}

	@Transactional
	public void updateBoard(Long id, Board reqBoard, User user) throws Exception {

		Board board = boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다.")); 
		if (!board.getCrtName().equals(user.getUsername())){throw new Exception("글 수정 실패 : 수정 권한이 없습니다.");}

		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
	}
	
	
	@Transactional
	public void deleteBoard(Long id, User user) throws Exception {
		Board board = boardRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다.")); 
		if (!board.getCrtName().equals(user.getUsername())){throw new Exception("글 삭제 실패 : 수정 권한이 없습니다.");}

		boardRepository.deleteById(id);
	}

	



}
