package com.atashgah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.auth.RegisterResponse;
import com.atashgah.config.JwtService;
import com.atashgah.dto.UserDTO;
import com.atashgah.dto.UserRegistrationDTO;
import com.atashgah.exception.PinSizeException;
import com.atashgah.exception.UserAlreadyExistsException;
import com.atashgah.exception.UserNotFoundException;
import com.atashgah.model.User;
import com.atashgah.service.CustomUserDetailsService;
import com.atashgah.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO user) {
		try {
			
			userService.registerUser(user);
			final UserDetails userDetails=userDetailsService.loadUserByUsername(user.getPin());
			final String jwt=jwtService.generateToken(userDetails.getUsername());
			RegisterResponse response=new RegisterResponse(jwt,"User registered successfully");
			
			
			return ResponseEntity.ok(response);
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
            UserDTO userDTO=new UserDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getPin(), user.getEmail());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
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
