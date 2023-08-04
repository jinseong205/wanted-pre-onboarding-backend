package com.example.server.user;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

	@Autowired
	protected MockMvc mvc;
	@Autowired
	protected ObjectMapper obejctMapper;

	public ResultActions createUser() throws Exception {
		
		ResultActions result = mvc
				.perform(post("/api/join").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"tester@test.com\",\"password\":\"testtest\"}"));
	
		return result;
	}
	
	@DisplayName("과제1 - 회원가입")
	public void join() throws Exception {
		ResultActions result = createUser();
		result.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.message", is("회원가입 성공")));

	}
	
	@DisplayName("과제2 - 로그인")
	public void login() throws Exception {
		
		ResultActions result = mvc
				.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"tester@test.com\",\"password\":\"testtest\"}"));
		result.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.message", is("로그인 성공")));

	}

}
