package com.Java.JUnitMockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import com.Java.JUnitMockito.Controller.UserController;
import com.Java.JUnitMockito.DTO.ResponseDto;
import com.Java.JUnitMockito.DTO.UserDto;
import com.Java.JUnitMockito.Exceptions.UserNotFoundException;
import com.Java.JUnitMockito.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebMvcTest(UserController.class)
class JUnitMockitoApplicationTests {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateUser() throws Exception {
		UserDto user1 = new UserDto.Builder().id(1).name("Rajat").age(25).build();

		mockMvc.perform(post("/v1/users/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user1))).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testGetUser() throws UserNotFoundException {
		UserDto user1 = new UserDto.Builder().id(1).name("Abhishek").age(27).build();
		when(userService.getUser(1)).thenReturn(user1);
		ResponseEntity<ResponseDto<UserDto>> responseEntity = userController.getUser(1);
		verify(userService, times(1)).getUser(1);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(user1, responseEntity.getBody().getData());
	}

}
