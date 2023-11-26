package com.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.UserDto;
import com.demo.exceptions.IncorrectCredentialException;
import com.demo.exceptions.UserNotFoundException;
import com.demo.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<UserDto> saveDepartment(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}

	@GetMapping("/authenticate")
	public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDto) throws Exception, IncorrectCredentialException {
		return userService.authenticate(userDto);
	}
	
	@GetMapping("/id/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") long id) throws UserNotFoundException {
		return userService.findUserById(id);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String name) throws UserNotFoundException {
		return userService.findUserByUsername(name);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) throws UserNotFoundException {
		return userService.findUserByEmail(email);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return userService.getAllUsers();
	}

	@PutMapping("/update/{userid}")
	public ResponseEntity<UserDto> updateUser( @PathVariable("userid") Long userid ,@RequestBody UserDto userDto ) throws UserNotFoundException {
            return userService.updateUser(userDto, userid);        
	}

	 @DeleteMapping("/delete/{userId}")
	    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
	        return userService.deleteUser(userId);
	    }
}
