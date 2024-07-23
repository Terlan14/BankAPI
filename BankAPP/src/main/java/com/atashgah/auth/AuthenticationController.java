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
	public ResponseEntity<?>createAuthenticationToken(@RequestHeader(value="Authorization",required=false) String token,@RequestBody AuthenticationRequest authenticationRequest){
		
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getPin());
		
		final String userJwt=jwtService.generateToken(userDetails.getUsername());
		String pin=null;
		if(token!=null &&token.startsWith("Bearer ")) {
			String jwt=token.substring(7);
			pin=jwtService.extractUsername(jwt);
		}
		if(!authenticationRequest.getPin().equals(pin)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or credentials");
		}
		try {
             Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getPin(), authenticationRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect PIN or password");
        }
		
		User user=(User)userDetails;
		AuthenticationResponse response=new AuthenticationResponse(userJwt,user);
		return ResponseEntity.ok(response);
	}
	

}
