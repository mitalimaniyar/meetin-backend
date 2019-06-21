package org.jeavio.meetin.backend.service.impl;

import java.util.List;

import org.jeavio.meetin.backend.dao.TeamRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.jeavio.meetin.backend.service.RoleService;
import org.jeavio.meetin.backend.service.TeamService;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;
	
	@Autowired
	TeamRepository teamRepository;
		
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Override
	public List<TeamDetails> find() {
		List<TeamDetails> teams=teamRepository.findAllTeams();
		for(TeamDetails team:teams) {
			Integer teamId=team.getTeamId();
			team.setTeamAdmins(userTeamRoleRepository.findTeamAdminsByTeamId(teamId));
			team.setTeamMembers(userTeamRoleRepository.findMembersByTeamId(teamId));	
			team.setIsAdmin(false);
		}
		return teams;
	}
	
	@Override
	public List<TeamDetails> find(String empId) {
		Integer userId=userService.findIdByEmpId(empId);
		List<TeamDetails> teams=userTeamRoleRepository.findTeamsByUserId(userId);
		for(TeamDetails team:teams) {
			Integer teamId=team.getTeamId();
			team.setTeamAdmins(userTeamRoleRepository.findTeamAdminsByTeamId(teamId));
			team.setTeamMembers(userTeamRoleRepository.findMembersByTeamId(teamId));	
			team.setIsAdmin(userTeamRoleRepository.isUserTeamAdmin(userId, teamId));
		}
		return teams;
	}

	@Override
	public List<UserInfo> findNonTeamMembers(Integer teamId) {
		return userTeamRoleRepository.finNonTeamMembers(teamId);
	}
	
	@Override
	public Team getTeamFromId(Integer teamId) {
		Team team=teamRepository.findById(teamId).orElse(new Team("No-Team"));
		return team;
	}
	
	@Override
	public void addTeamMembers(Integer teamId,List<String> empIds) {
	Team team=getTeamFromId(teamId);
	Role role=roleService.getTeamMemberRole();
	for(String empId:empIds) {
		User user=userService.findUserByEmpId(empId);
		userTeamRoleRepository.save(new UserTeamRole(user, team, role));
	}
}
	
	@Override
	public void removeTeamMember(Integer teamId,String empId) {
		Integer userId=userService.findIdByEmpId(empId);
		userTeamRoleRepository.deleteByUserId(userId, teamId);
	}
	
}
