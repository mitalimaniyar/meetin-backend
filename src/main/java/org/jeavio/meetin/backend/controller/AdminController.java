package org.jeavio.meetin.backend.controller;

import java.util.Map;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@RequestMapping(method = RequestMethod.POST,path = "/api/admin/promote")
	public ResponseEntity<?> promoteSuperAdmin(@RequestBody Map<String,String> body){
		ResponseEntity<?> response = null;
		String empId = body.get("EmpId");
		if(adminService.promoteSuperAdmin(empId)) {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
		}else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Unable to execute request"));
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "/api/admin/promote/teamadmin")
	public ResponseEntity<?> promoteTeamAdmin(@RequestBody Map<String,Object> body){
		ResponseEntity<?> response = null;
		String empId = (String) body.get("EmpId");
		Integer teamId = (Integer) body.get("teamId");
		if(adminService.promoteTeamAdmin(teamId, empId)) {
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success"));
		}else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Unable to execute request"));
		}
		return response;
	}
	
}
