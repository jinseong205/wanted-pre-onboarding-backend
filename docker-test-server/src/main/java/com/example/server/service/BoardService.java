package com.example.server.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	public void saveBoard(Board board) { // title, content
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> findBoardAll(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board boardDetail(Long id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글을 찾을수 었습니다.");
		});
	}

	@Transactional
	public void deleteBoard(Long id, User user) throws Exception {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 삭제 실패 : 게시글 id를 찾을 수 없습니다");
		});

		if (!board.getCrtName().equals(user.getUsername())){
			throw new Exception("글 삭제 실패 : 삭제 권한이 없습니다.");
		}

		boardRepository.deleteById(id);
	}

	@Transactional
	public void updateBoard(Long id, Board requestBoard, User user) throws Exception {

		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글을 찾을수 었습니다.");
		}); // 영속화 완료

		if (!board.getCrtName().equals(user.getUsername())){
			throw new Exception("글 수정 실패 : 수정 권한이 없습니다.");
		}

		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());

		// 해당 함수로 종료시에 Service가 종료될 때 트랜잭션이 종료됩니다.
		// 이때 더티 체킹 - 자동 업데이트가 됨. db flush
	}


}
