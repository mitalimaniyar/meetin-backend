package org.jeavio.meetin.backend.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;



public class LoginRequest {
	
    @NotBlank
    @NonNull
    private String username;

    @NotBlank
    @NonNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
