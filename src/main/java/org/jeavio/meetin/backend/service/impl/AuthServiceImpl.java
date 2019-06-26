package org.jeavio.meetin.backend.service.impl;


import org.jeavio.meetin.backend.dto.LoginRequest;
import org.jeavio.meetin.backend.security.JwtTokenProvider;
import org.jeavio.meetin.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Override
	public String verifyUser(LoginRequest loginRequest){
		 Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String jwtToken = tokenProvider.generateToken(authentication);

			return jwtToken;
	}
}
