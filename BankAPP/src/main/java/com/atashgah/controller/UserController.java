package com.atashgah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.exception.PinSizeException;
import com.atashgah.exception.UserAlreadyExistsException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.User;
import com.atashgah.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user) {
		try {
			userService.registerUser(user);
			return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
		} 
		catch(UserAlreadyExistsException ex){
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
		}
		catch(PinSizeException ex){
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		catch(Exception ex) {
			return new ResponseEntity<>("An error occured during registration",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{pin}")
	public ResponseEntity<?> getUserByPin(@PathVariable String pin) {
        try {
            User user = userService.getUserByPin(pin);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	@DeleteMapping("/delete/{pin}")
	public ResponseEntity<?>deleteUserByPin(@PathVariable String pin){
		try {
			
			userService.deleteUser(pin);
			return new ResponseEntity<>("User is deleted",HttpStatus.OK);
		}
		catch(UserNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			return new ResponseEntity<>("After deleting specific user you should delete related bank accounts "
					+ "and transfers", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

}
