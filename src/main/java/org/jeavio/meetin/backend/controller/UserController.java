package org.jeavio.meetin.backend.controller;

import java.util.List;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/users/me")
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

	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/users")
	public ResponseEntity<?> getAllUsers(){
		ResponseEntity<?> response = null;
		List<UserInfo> users = userService.findAll();
		response =  ResponseEntity.status(HttpStatus.OK).body(users);
		return response;
	}
	
	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS','CREATE_ACCESS')")
	@RequestMapping(method = RequestMethod.POST, path = "/api/users")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		if (!userService.existsByEmpId(user.getEmpId()) && !userService.existsByUsername(user.getUsername())) {
			userService.addUser(user);
			response = ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "User Created"));

		} else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "User with empId or username already exists."));

		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS','DELETE_ACCESS')")
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/users")
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

	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS','MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.PUT, path = "/api/users")
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

		} else if (!(userService.findUserById(userId).getUsername()).equals(modifiedUsername)
				&& userService.existsByUsername(modifiedUsername)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Username already exists."));

		} else {
			userService.modifyUserDetails(modifiedUser);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User details modified."));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'USERS_PASSWORD','MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.POST, path = "/api/users/my/changepwd")
	public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
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
			int status = userService.changePassword(empId,body);
			if (status == 200 || status ==400) {
				response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Change Password Successful"));
			}else {
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(500, "Unable to change password."));
			}
		}
		return response;
	}
}
