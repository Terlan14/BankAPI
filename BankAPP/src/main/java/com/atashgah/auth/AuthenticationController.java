package com.atashgah.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atashgah.config.JwtService;
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
	public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
		try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getPin(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect PIN or password");
        }
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getPin());
		final String jwt=jwtService.generateToken(userDetails.getUsername());
		
		User user=(User)userDetails;
		AuthenticationResponse response=new AuthenticationResponse(jwt,user);
		return ResponseEntity.ok(response);
	}

}
