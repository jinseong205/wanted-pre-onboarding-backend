package com.example.server.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtProperties {
	
	@Value("${jwt.token.secret}")
	private String SECRET;; // 우리 서버만 알고 있는 비밀값
	
	@Value("${jwt.token.expiry-seconds}")
	private int EXPIRATION_TIME; // 10일 (1/1000초)
	
	private String TOKEN_PREFIX = "Bearer ";
	private String HEADER_STRING = "Authorization";
}
