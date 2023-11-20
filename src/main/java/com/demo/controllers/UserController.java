package com.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.User;
import com.demo.entities.UserDto;
import com.demo.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	// save user in database(db)
	@PostMapping
	public ResponseEntity<UserDto> saveDepartment(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
	}

	// Retrieve user by id from db
	@GetMapping("/id/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") long id) {
		return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
	}

	// Retrieve user by username from db
	@GetMapping("/username/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String name) {
		return new ResponseEntity<>(userService.findUserByUsername(name), HttpStatus.OK);
	}

	// Retrieve user by email from db
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
		return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
	}

	// get all users as a list
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	//update user in db by id
	@PutMapping("/update/{userid}")
	public ResponseEntity<UserDto> updateUser( @PathVariable("userid") Long userid ,@RequestBody UserDto userDto ) {
		
		UserDto updatedUser = userService.updateUser(userDto, userid);
		if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	//delete user by id
	 @DeleteMapping("/delete/{userId}")
	    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
	        boolean deleted = userService.deleteUser(userId);

	        if (deleted) {
	            return ResponseEntity.ok("User deleted successfully");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
