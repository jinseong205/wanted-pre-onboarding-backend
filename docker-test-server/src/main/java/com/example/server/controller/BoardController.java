package com.example.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;


	@GetMapping("/board/boardList")
	public String boardGroupByCategory(@RequestParam(name = "category", defaultValue = "0") int id,
			@PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {

		
		return "board/boardList"; // viewResolver 작동!
	}

	// USER 권한이 필요
	@GetMapping("/board/boardDetail/{id}")
	public String boardDetail(@PathVariable int id) {
		return "board/boardDetail";
	}

	@GetMapping("/board/updateForm/{id}")
	public String updateForm(@PathVariable int id) {
		return "board/updateForm";
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}


}
