package org.jeavio.meetin.backend.controller;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dto.LoginRequest;
import org.jeavio.meetin.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

       ResponseEntity<?> response=authService.verifyUser(loginRequest);
       return response;
    }
}
