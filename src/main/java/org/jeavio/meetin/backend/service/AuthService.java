package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dto.JwtAuthenticationResponse;
import org.jeavio.meetin.backend.dto.LoginRequest;
import org.jeavio.meetin.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	public ResponseEntity<?> verifyUser(LoginRequest loginRequest){
		 Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String jwtToken = tokenProvider.generateToken(authentication);

	        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.OK).body(new JwtAuthenticationResponse(jwtToken));

			return response;
	}
}
