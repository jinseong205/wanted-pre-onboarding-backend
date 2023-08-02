package com.example.server.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data						//Getter Setter
public class UserFormDto {
	/* 회원 가입 전용 DTO (req) */
	
	@NotBlank(message = "아이디를 입력해주세요.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String username;	
	
	@NotBlank(message = "비밀번호는를 입려해주세요.")
	@Length(min=8, message = "패스워드는 8자 이상으로 입력하세요.")
	private String password;	


}
	
