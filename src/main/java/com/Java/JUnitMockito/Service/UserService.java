package com.Java.JUnitMockito.Service;

import java.util.List;

import com.Java.JUnitMockito.DTO.UserDto;
import com.Java.JUnitMockito.Exceptions.UserNotFoundException;

public interface UserService {
	
	UserDto getUser(Integer userId) throws UserNotFoundException;
	
	List<UserDto> getAllUsers() throws UserNotFoundException;
	
	UserDto createUser(UserDto userDto) throws UserNotFoundException;
	
	UserDto updateUser(UserDto userDto) throws UserNotFoundException;
	
	void deleteUser(Integer userId) throws UserNotFoundException;
	
}
