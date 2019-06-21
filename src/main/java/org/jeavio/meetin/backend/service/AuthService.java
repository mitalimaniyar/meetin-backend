package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dto.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

	ResponseEntity<?> verifyUser(LoginRequest loginRequest);

}