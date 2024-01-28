package com.Java.JUnitMockito.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Java.JUnitMockito.DTO.ResponseDto;
import com.Java.JUnitMockito.DTO.UserDto;
import com.Java.JUnitMockito.Exceptions.UserNotFoundException;
import com.Java.JUnitMockito.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<ResponseDto<UserDto>> getUser(@PathVariable Integer userId) throws UserNotFoundException {
		logger.info("UserController :: Inside getUser");
		UserDto user = userService.getUser(userId);
		ResponseDto<UserDto> responseDto = new ResponseDto.Builder<UserDto>()
				.status(String.valueOf(HttpStatus.OK.value())).message(HttpStatus.OK.series().name()).data(user)
				.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<ResponseDto<List<UserDto>>> getAllUsers() throws UserNotFoundException {
		logger.info("UserController :: Inside getAllUsers");
		List<UserDto> users = userService.getAllUsers();
		ResponseDto<List<UserDto>> responseDto = new ResponseDto.Builder<List<UserDto>>()
				.status(String.valueOf(HttpStatus.OK.value())).message(HttpStatus.OK.series().name()).data(users)
				.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto<UserDto>> createUsers(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {
		logger.info("UserController :: Inside createUsers");
		UserDto user = userService.createUser(userDto);
		ResponseDto<UserDto> responseDto = new ResponseDto.Builder<UserDto>()
				.status(String.valueOf(HttpStatus.OK.value())).message(HttpStatus.OK.getReasonPhrase()).data(user)
				.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<ResponseDto<UserDto>> updateUser(@Valid @RequestBody UserDto userDto) throws UserNotFoundException {
		logger.info("UserController :: Inside updateUser");
		UserDto user = userService.updateUser(userDto);
		ResponseDto<UserDto> responseDto = new ResponseDto.Builder<UserDto>()
				.status(String.valueOf(HttpStatus.OK.value())).message(HttpStatus.OK.getReasonPhrase()).data(user)
				.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseDto<?>> deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
		logger.info("UserController :: Inside deleteUser");
		userService.deleteUser(userId);
		ResponseDto<UserDto> responseDto = new ResponseDto.Builder<UserDto>()
				.status(String.valueOf(HttpStatus.OK.value())).message(HttpStatus.OK.getReasonPhrase()).data(null)
				.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
