package com.demo.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.dto.UserDto;
import com.demo.entities.User;
import com.demo.exceptions.IncorrectCredentialException;
import com.demo.exceptions.UserNotFoundException;
import com.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;


	@Autowired
	ModelMapper mapper;

	public ResponseEntity<UserDto> saveUser(UserDto dto) {

		User user = mapToUser(dto);
		User newUser = userRepo.save(user);
		UserDto responseDto = mapToDto(newUser);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	public ResponseEntity<String> authenticate(UserDto dto) throws UserNotFoundException, IncorrectCredentialException {
		Long id = dto.getId();
		User existingUser = userRepo.findById(dto.getId())
				.orElseThrow(() -> new UserNotFoundException("User not found by this ID: " + id));

		if (dto.getPassword().equals( existingUser.getPassword())
				&& dto.getUsername().equals(existingUser.getUsername())) {
			return new ResponseEntity<>("User Exist", HttpStatus.OK);
		} else {
			throw new IncorrectCredentialException("Enter correct details");
		}

	}


	public ResponseEntity<UserDto> findUserById(long id) throws UserNotFoundException {

		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found by this ID: " + id));
		UserDto responseDto = mapToDto(user);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);

	}


	public ResponseEntity<UserDto> findUserByUsername(String name) throws UserNotFoundException {

		User user = userRepo.findByUsername(name)
				.orElseThrow(() -> new UserNotFoundException("User not found with this name: " + name));
		UserDto responseDto = mapToDto(user);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);

	}


	public ResponseEntity<UserDto> findUserByEmail(String email) throws UserNotFoundException {

		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found with this email: " + email));
		UserDto responseDto = mapToDto(user);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);

	}

	public ResponseEntity<List<UserDto>> getAllUsers() {

		List<User> userList = userRepo.findAll();
		List<UserDto> dtoList = userList.stream().map(user -> mapToDto(user)).toList();
		return new ResponseEntity<>(dtoList, HttpStatus.OK);

	}


	public ResponseEntity<UserDto> updateUser(UserDto dto, Long id) throws UserNotFoundException {

		User existingUser = userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found by this ID: " + id));

		existingUser.setEmail(dto.getEmail());
		existingUser.setUsername(dto.getUsername());
		
		
		User updatedUser = userRepo.save(existingUser);
		UserDto responseDto = mapToDto(updatedUser);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

	}


	public ResponseEntity<String> deleteUser(Long userId) throws UserNotFoundException {
		User existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found by this ID: " + userId));
		userRepo.deleteById(userId);
		return new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);

	}


	public UserDto mapToDto(User User) {
		UserDto dto = mapper.map(User, UserDto.class);
		dto.setPassword("*****");     
		return dto;
	}

	public User mapToUser(UserDto dto) {
		User user = mapper.map(dto, User.class);
		return user;
	}
}