package com.example.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.ResultDto;
import com.example.server.dto.UserFormDto;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @GetMapping("/")
    public ResponseEntity<?> main() {
        return ResponseEntity.ok().body("main");
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAll() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userList);
    }
    
	@PostMapping("/api/join")
	public ResponseEntity<?> join(@RequestBody @Valid UserFormDto userFormDto, BindingResult bindingResult)
			throws Exception {

		if (bindingResult.hasErrors())
			throw new Exception(bindingResult.getFieldError().getDefaultMessage());
		User user;
		user = User.builder().username(userFormDto.getUsername()).password(bCryptPasswordEncoder.encode(userFormDto.getPassword())).roles("ROLE_USER").build();
		userService.join(user);
		return new ResponseEntity<>(new ResultDto("회원가입 성공"), HttpStatus.CREATED);
	}
}
