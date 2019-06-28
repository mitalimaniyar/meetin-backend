package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Team;

public interface TeamService {

	public List<TeamDetails> find();

	public List<TeamDetails> find(String empId);

	public List<UserInfo> findNonTeamMembers(Integer teamId);

	public Team getTeamFromId(Integer teamId);

	public void addTeamMembers(Integer teamId, List<String> empIds);

	public void removeTeamMember(Integer teamId, String empId);

	public List<UserInfo> findTeamMembers(Integer teamId);

	public List<UserInfo> findTeamMembers(String teamName);
	
	public boolean existsByTeamName(String teamName);
	
	public boolean existsByTeamId(Integer teamId);
	
	public void addTeam(String teamName);
	
	public void removeTeam(String teamName);
	
	public void updateTeamName(Integer teamId,String newTeamName);

	public List<String> getTeamNames();
}