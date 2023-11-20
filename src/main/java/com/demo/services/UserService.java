package com.demo.services;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.User;
import com.demo.entities.UserDto;
import com.demo.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper mapper;
	
	public UserDto saveUser(UserDto dto){
		User user = mapToUser(dto);
		User newUser = userRepo.save(user);
		UserDto responseDto = mapToDto(newUser); 
		return responseDto;
	}
	
	public UserDto findUserById(long id) {
		User user = userRepo.findById(id).orElse(null);
		UserDto userDto = mapToDto(user);
		return userDto;
	}
	
	public UserDto findUserByUsername(String name) {
		User user = userRepo.findByUsername(name);
		UserDto userDto = mapToDto(user);
		return userDto;
	}
	
	public UserDto findUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		UserDto userDto = mapToDto(user);
		return userDto;
	}
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public UserDto updateUser(UserDto dto ,Long id) {
		User existingUser = userRepo.findById(id).orElse(null);
		User newUser = mapToUser(dto);
		if(existingUser!=null) {
			existingUser.setEmail(newUser.getEmail());
			existingUser.setUsername(newUser.getUsername());
			existingUser.setPassword(newUser.getPassword());
			
			User updatedUser =  userRepo.save(existingUser);
			UserDto responseDto = mapToDto(updatedUser); 
			return responseDto;
		}
		else {
			return null;
		}
	
	}
	

	public boolean deleteUser(Long userId) {
	        if (userRepo.existsById(userId)) {
	            userRepo.deleteById(userId);
	            return true;
	        } else {
	            return false;
	        }
	
	}
	

	public UserDto mapToDto(User User) {
		UserDto dto = mapper.map(User, UserDto.class);
		return dto;
	}
	public User mapToUser(UserDto dto) {
		User user = mapper.map(dto, User.class);
		return user;
	}
}