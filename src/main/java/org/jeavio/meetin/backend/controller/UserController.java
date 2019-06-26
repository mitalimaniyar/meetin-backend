package org.jeavio.meetin.backend.controller;

import java.util.Map;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.UserDTO;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/api/user/me")
	public ResponseEntity<?> getProfile() {
		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		if (!userService.existsByEmpId(empId)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Unable to find requested user."));
		} else {
			UserInfo userInfo = userService.findProfileByEmpId(empId);
			if (userInfo == null)
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(500, "Unable to find requested user."));
			else
				response = ResponseEntity.status(HttpStatus.OK).body(userInfo);
		}
		return response;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/api/user")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		if (!userService.existsByEmpId(user.getEmpId()) && !userService.existsByUsername(user.getUsername())) {
			userService.addUser(user);
			response = ResponseEntity.status(HttpStatus.OK).body("User Created");
			
		} else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "User with empId or username already exists."));
			
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/api/user")
	public ResponseEntity<?> removeUser(@RequestBody Map<String, String> body) {
		ResponseEntity<?> response = null;
		String empId = body.get("empId");
		if (userService.existsByEmpId(empId)) {
			userService.removeUser(empId);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User removed."));
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "User not found"));
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/api/user")
	public ResponseEntity<?> modifyUser(@Valid @RequestBody UserDTO modifiedUser) {
		ResponseEntity<?> response = null;
		Integer userId = modifiedUser.getId();
		String modifiedEmpId = modifiedUser.getEmpId();
		String modifiedUsername = modifiedUser.getUsername();
		if (!userService.existsById(userId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "User not found"));
			return response;
		} else if (!(userService.findUserById(userId).getEmpId()).equals(modifiedEmpId)
				&& userService.existsByEmpId(modifiedEmpId)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Employee with Id already exists."));

		}  else if (!(userService.findUserById(userId).getUsername()).equals(modifiedUsername)
				&& userService.existsByUsername(modifiedUsername)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Username already exists."));

		} else {
			userService.modifyUserDetails(modifiedUser);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User details modified."));
		}
		return response;
	}
}
