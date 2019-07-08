package org.jeavio.meetin.backend.controller;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@PreAuthorize("@authorizationManager.authorize(authentication,'ADMIN','CREATE_ACCESS')")
	@RequestMapping(method = RequestMethod.POST,path = "/promote")
	public ResponseEntity<?> promoteSuperAdmin(@RequestBody Map<String,String> body){
		ResponseEntity<?> response = null;
		String empId = body.get("empId");
		if(adminService.promoteSuperAdmin(empId)) {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
		}else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Unable to execute request"));
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("@authorizationManager.authorize(authentication,'ADMIN','MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.POST,path = "/promote/teamadmin")
	public ResponseEntity<?> promoteTeamAdmin(@RequestBody Map<String,Object> body){
		ResponseEntity<?> response = null;
		List<String> empIds = (List<String>) body.get("empIds");
		Integer teamId = Integer.parseInt(body.get("teamId").toString());
		if(adminService.promoteTeamAdmin(teamId, empIds)) {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
		}else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Unable to execute request"));
		}
		return response;
	}
	
}
