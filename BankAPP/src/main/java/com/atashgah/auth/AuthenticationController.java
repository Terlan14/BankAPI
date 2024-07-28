package com.atashgah.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.config.JwtService;
import com.atashgah.dto.UserLoginResponse;
import com.atashgah.dto.UserMapper;
import com.atashgah.dto.UserRegistrationDTO;
import com.atashgah.model.User;
import com.atashgah.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<?>createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest){
		
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getPin());
		
		final String userJwt=jwtService.generateToken(authenticationRequest.getPin());
		
		try {
             Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getPin(), authenticationRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect PIN or password");
        }
		
		User user=(User)userDetails;
		UserLoginResponse result=UserMapper.toLoginResponse(user);
		AuthenticationResponse response=new AuthenticationResponse(userJwt,result);
		return ResponseEntity.ok(response);
	}
	

}
