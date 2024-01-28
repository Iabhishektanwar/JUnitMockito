package com.Java.JUnitMockito.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Java.JUnitMockito.DTO.UserDto;
import com.Java.JUnitMockito.Entity.User;
import com.Java.JUnitMockito.Exceptions.UserNotFoundException;
import com.Java.JUnitMockito.ModelMapper.EntityDtoConverter;
import com.Java.JUnitMockito.Repository.UserRepository;

@Service
public class UserServiceImple implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImple.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityDtoConverter entityDtoConverter;

	@Override
	public UserDto getUser(Integer userId) throws UserNotFoundException {
		logger.info("UserServiceImple :: Inside getUser");
		Optional<User> opt = userRepository.findById(userId);
		if (opt.isPresent()) {
			return entityDtoConverter.convertToDto(opt.get(), UserDto.class);
		}
		throw new UserNotFoundException("User not found with is : " + userId);
	}

	@Override
	public List<UserDto> getAllUsers() throws UserNotFoundException {
		logger.info("UserServiceImple :: Inside getAllUsers");
		List<User> users = userRepository.findAll();
		return users.stream().map(user -> entityDtoConverter.convertToDto(user, UserDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDto createUser(UserDto userDto) throws UserNotFoundException {
		logger.info("UserServiceImple :: Inside createUser");
		User user = entityDtoConverter.convertToEntity(userDto, User.class);
		return entityDtoConverter.convertToDto(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto) throws UserNotFoundException {
		logger.info("UserServiceImple :: Inside updateUser");
		Optional<User> opt = userRepository.findById(userDto.getId());
		if (opt.isEmpty())
			throw new UserNotFoundException("User not found with is : " + userDto.getId());

		User user = opt.get();
		user.setName(userDto.getName());
		user.setAge(userDto.getAge());

		return entityDtoConverter.convertToDto(userRepository.save(user), UserDto.class);
	}

	@Override
	public void deleteUser(Integer userId) throws UserNotFoundException {
		logger.info("UserServiceImple :: Inside deleteUser");
		Optional<User> opt = userRepository.findById(userId);
		if (opt.isEmpty())
			throw new UserNotFoundException("User not found with is : " + userId);
		User user = opt.get();
		userRepository.delete(user);
	}

}
