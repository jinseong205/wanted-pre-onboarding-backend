package com.example.server.board;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardControllerTest {

	@Autowired
	protected MockMvc mvc;

	public String getToken() throws Exception {
		
		ResultActions result = mvc
				.perform(post("/api/join").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"tester@test.com\",\"password\":\"testtest\"}"));
	
		String token = mvc
				.perform(post("/api/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"tester@test.com\",\"password\":\"testtest\"}"))
				.andReturn().getResponse().getHeader("Authorization");
						
		return token;
	}

	public ResultActions createBoard() throws Exception {
		
		String token = getToken();
		
		ResultActions result = mvc
				.perform(post("/api/board").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
						.content("{\"title\":\"title1\",\"content\":\"content1\"}"));

		return result;
	}
	
	@Test
	@DisplayName("과제3 - 게시글 생성")
	@Order(3)
	public void createBoardTest() throws Exception {
		
		String token = getToken();
		
		ResultActions result = createBoard();
		result.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.message", is("게시물 작성 성공")));

	}


	@Test
	@DisplayName("과제4 - 게시글 조회")
	@Order(4)
	public void retrieveBoards() throws Exception {
		
		ResultActions result = mvc
				.perform(get("/api/boards?page=0").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].title", is("title1")));

	}
	
	

	@Test
	@DisplayName("과제5 - 게시글 단일 조회")
	@Order(5)
	public void retrieveBoard() throws Exception {
		
		
		ResultActions result = mvc
				.perform(get("/api/board/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("title1")));

	}

	@Test
	@DisplayName("과제6 - 게시글 수정")
	@Order(6)
	public void updateBoard() throws Exception {
		
		String token = getToken();
		
		ResultActions result = mvc
				.perform(put("/api/board/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
						.content("{\"title\":\"title1\",\"content\":\"content2\"}"));
		result.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.message", is("게시물 수정 성공")));


	}
	
	@Test
	@DisplayName("과제7 - 게시글 삭제")
	@Order(7)
	public void deleteBoard() throws Exception {
		
		String token = getToken();
		
		ResultActions result = mvc
				.perform(delete("/api/board/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token));
		result.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("게시물 삭제 성공")));
		

	}
	
}
