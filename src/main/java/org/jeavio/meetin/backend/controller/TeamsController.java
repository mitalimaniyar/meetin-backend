package org.jeavio.meetin.backend.controller;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamsController {

	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/teams")
	public ResponseEntity<?> getAllTeams(){
		List<TeamDetails> teams = teamService.find();
		ResponseEntity<?> response=ResponseEntity.status(HttpStatus.OK).body(teams);
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/teams/{empId}")
	public ResponseEntity<?> getTeams(@PathParam("empId") String empId){
		List<TeamDetails> teams = teamService.find(empId);
		ResponseEntity<?> response=ResponseEntity.status(HttpStatus.OK).body(teams);
		return response;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/api/teams/{teamId}/addmembers")
	public ResponseEntity<?> addTeamMemberRequest(@PathParam("teamId") Integer teamId){
		List<UserInfo> nonTeamMembers=teamService.findNonTeamMembers(teamId);
		ResponseEntity<?> response=ResponseEntity.status(HttpStatus.OK).body(nonTeamMembers);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST,path = "/api/teams/{teamId}/add")
	public ResponseEntity<?> addTeamMember(@RequestBody Map<String,Object> body){
//		List<String>
		return null;
	}
//	@RequestMapping
//	public ResponseEntity<?> addTeam(@RequestBody )
//	remove team
	
	
}
