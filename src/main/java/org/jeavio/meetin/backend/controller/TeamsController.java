package org.jeavio.meetin.backend.controller;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamsController {

	@Autowired
	private TeamService teamService;

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/teams")
	public ResponseEntity<?> getAllTeams() {
		List<TeamDetails> teams = teamService.find();
		ResponseEntity<?> response = null;
		if (!teams.isEmpty())
			response = ResponseEntity.status(HttpStatus.OK).body(teams);
		else
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "No Records Found"));
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','CREATE_ACCESS')")
	@RequestMapping(method = RequestMethod.POST, path = "/api/teams")
	public ResponseEntity<?> addTeam(@RequestBody Map<String, String> body) {
		ResponseEntity<?> response = null;
		String teamName = body.get("teamName");
		if (!teamService.existsByTeamName(teamName)) {
			teamService.addTeam(teamName);
			response = ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Team Created"));
		} else {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Team already exist with same name."));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','DELETE_ACCESS')")
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/teams")
	public ResponseEntity<?> removeTeam(@RequestBody Map<String, String> body) {
		ResponseEntity<?> response = null;
		String teamName = body.get("teamName");
		if (teamService.existsByTeamName(teamName)) {
			teamService.removeTeam(teamName);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Team Removed."));
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Team not found."));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.PUT, path = "/api/teams")
	public ResponseEntity<?> modifyTeamName(@RequestBody Map<String, Object> body) {
		ResponseEntity<?> response = null;
		Integer teamId = (Integer) body.get("teamId");
		String newTeamName = (String) body.get("teamName");

		if (!teamService.existsByTeamId(teamId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(404, "Requested team not found."));
		} else if (teamService.getTeamFromId(teamId).getTeamName().equals(newTeamName)
				&& teamService.existsByTeamName(newTeamName)) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(500, "Team name already exists."));
		} else {
			teamService.updateTeamName(teamId, newTeamName);
			response = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Team Details modified."));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/teams/my")
	public ResponseEntity<?> getTeams() {

		ResponseEntity<?> response = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse(401, "Authorization Required."));
		}
		String empId = ((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmpId();
		List<TeamDetails> teams = teamService.find(empId);
		if (!teams.isEmpty())
			response = ResponseEntity.status(HttpStatus.OK).body(teams);
		else
			response = ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(404, "No Records Found For Specified Employee."));
		return response;

	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAM_OPS_'.concat(#teamId),'VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/teams/{teamId}/add")
	public ResponseEntity<?> getNonTeamMembers(@PathVariable(name = "teamId") Integer teamId) {

		ResponseEntity<?> response = null;
		if (!teamService.existsByTeamId(teamId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Team not found."));
		} else {
			List<UserInfo> nonTeamMembers = teamService.findNonTeamMembers(teamId);
			response = ResponseEntity.status(HttpStatus.OK).body(nonTeamMembers);
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAM_OPS_'.concat(#teamId),'VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/teams/{teamId}/info")
	public ResponseEntity<?> getTeamMembers(@PathVariable(name = "teamId") Integer teamId) {
		ResponseEntity<?> response = null;
		if (!teamService.existsByTeamId(teamId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Team not found."));
		} else {
			List<UserInfo> teamMembers = teamService.findTeamMembers(teamId);
			response = ResponseEntity.status(HttpStatus.OK).body(teamMembers);
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAM_OPS_'.concat(#teamId),'MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.POST, path = "/api/teams/{teamId}/addmembers")
	public ResponseEntity<?> addTeamMembers(@RequestBody Map<String, List<String>> body,
			@PathVariable(name = "teamId") Integer teamId) {
		ResponseEntity<?> response = null;
		List<String> empIds = body.get("empIds");
		if (!teamService.existsByTeamId(teamId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Team not found."));
		} else {
			teamService.addTeamMembers(teamId, empIds);
			response = ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Success"));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAM_OPS_'.concat(#teamId),'MODIFY_ACCESS')")
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/teams/{teamId}/remove")
	public ResponseEntity<?> removeTeamMember(@RequestBody Map<String,  List<String>> body,
			@PathVariable(name = "teamId") Integer teamId) {
		ResponseEntity<?> response = null;
		List<String> empIds = body.get("empIds");
		if (!teamService.existsByTeamId(teamId)) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Team not found."));
		} else {
			teamService.removeTeamMembers(teamId, empIds);
			response = ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(200, "Removed requested team member."));
		}
		return response;
	}

	@PreAuthorize("@authorizationManager.authorize(authentication,'TEAMS','VIEW_ACCESS')")
	@RequestMapping(method = RequestMethod.GET, path = "/api/teams/name")
	public ResponseEntity<?> getTeamNames() {
		ResponseEntity<?> response = null;
		List<String> teams = teamService.getTeamNames();
		if (teams.isEmpty())
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "No Records Found"));
		else
			response = ResponseEntity.status(HttpStatus.OK).body(teams);
		return response;
	}
}
