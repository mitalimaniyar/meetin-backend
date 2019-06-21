package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Team;

public interface TeamService {

	List<TeamDetails> find();

	List<TeamDetails> find(String empId);

	List<UserInfo> findNonTeamMembers(Integer teamId);

	Team getTeamFromId(Integer teamId);

	void addTeamMembers(Integer teamId, List<String> empIds);

	void removeTeamMember(Integer teamId, String empId);

}