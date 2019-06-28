package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dto.LoginRequest;

public interface AuthService {

	public String verifyUser(LoginRequest loginRequest);

}