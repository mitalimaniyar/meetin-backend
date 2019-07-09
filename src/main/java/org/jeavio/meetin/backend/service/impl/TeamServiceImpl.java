package org.jeavio.meetin.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.security.core.userdetails.UserDetails;
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
		List<TeamDetails> teams = teamRepository.findAllTeams();
		for (TeamDetails team : teams) {
			Integer teamId = team.getTeamId();
			team.setTeamAdmins(userTeamRoleRepository.findTeamAdminsByTeamId(teamId));
			team.setTeamMembers(userTeamRoleRepository.findMembersByTeamId(teamId));
			team.setIsAdmin(false);
		}
		return teams;
	}

	@Override
	public List<TeamDetails> find(String empId) {
		if (!userService.existsByEmpId(empId))
			return new ArrayList<>();

		Integer userId = userService.findIdByEmpId(empId);
		if (!userService.existsById(userId)) {
			return new ArrayList<>();
		}
		List<TeamDetails> teams = userTeamRoleRepository.findTeamsByUserId(userId);

		for (TeamDetails team : teams) {
			Integer teamId = team.getTeamId();
			team.setTeamAdmins(userTeamRoleRepository.findTeamAdminsByTeamId(teamId));
			team.setTeamMembers(userTeamRoleRepository.findMembersByTeamId(teamId));
			team.setIsAdmin(userTeamRoleRepository.isUserTeamAdmin(userId, teamId));
		}
		return teams;
	}

	@Override
	public List<UserInfo> findNonTeamMembers(Integer teamId) {
		if (!existsByTeamId(teamId))
			return new ArrayList<>();
		Set<String> memberIds = userTeamRoleRepository.findTeamMembers(teamId).stream().map(member -> member.getEmpId())
				.collect(Collectors.toSet());
		return userService.findAll().stream().filter(user -> !memberIds.contains(user.getEmpId()))
				.collect(Collectors.toList());
	}

	@Override
	public Team getTeamFromId(Integer teamId) {
		if (!existsByTeamId(teamId))
			return null;
		Team team = teamRepository.findById(teamId).get();
		return team;
	}

	@Override
	public void addTeamMembers(Integer teamId, List<String> empIds) {
		if (!existsByTeamId(teamId))
			return;
		for (String empId : empIds) {
			if (userService.existsByEmpId(empId)) {
				addTeamMember(teamId, empId);
			}
		}
	}

	@Override
	public void addTeamMember(Integer teamId, String empId) {
		if (!existsByTeamId(teamId) || !userService.existsByEmpId(empId))
			return;
		Team team = getTeamFromId(teamId);
		Role role = roleService.getTeamMemberRole();
		User user = userService.findByEmpId(empId);
		if (user != null && !userTeamRoleRepository.existsByUserAndTeamAndRole(user, team, role))
			userTeamRoleRepository.save(new UserTeamRole(user, team, role));

	}

	@Override
	public void removeTeamMembers(Integer teamId, List<String> empIds) {

		if (!existsByTeamId(teamId))
			return;
		for (String empId : empIds) {
			if (userService.existsByEmpId(empId))
				removeTeamMember(teamId, empId);

		}
	}

	@Override
	public void removeTeamMember(Integer teamId, String empId) {
		if (!existsByTeamId(teamId) || !userService.existsByEmpId(empId))
			return;
		Integer userId = userService.findIdByEmpId(empId);
		userTeamRoleRepository.deleteByUserId(userId, teamId);
	}

	@Override
	public List<UserInfo> findTeamMembers(Integer teamId) {
		if (!existsByTeamId(teamId))
			return new ArrayList<>();
		return userTeamRoleRepository.findTeamMembers(teamId);
	}

	@Override
	public boolean existsByTeamName(String teamName) {
		return teamRepository.existsByTeamName(teamName);
	}

	@Override
	public boolean existsByTeamId(Integer teamId) {
		return teamRepository.existsById(teamId);
	}

	@Override
	public void addTeam(String teamName) {
		if (!existsByTeamName(teamName)) {
			Team team = new Team(teamName);
			teamRepository.save(team);
		}
	}

	@Override
	public void removeTeam(String teamName) {
		if (existsByTeamName(teamName))
			teamRepository.deleteByTeamName(teamName);
	}

	@Override
	public void updateTeamName(Integer teamId, String newTeamName) {
		if (existsByTeamId(teamId) && !existsByTeamName(newTeamName)) {
			Team team = teamRepository.findById(teamId).get();
			team.setTeamName(newTeamName);
			teamRepository.save(team);
		}
	}

	@Override
	public List<UserInfo> findTeamMembers(String teamName) {
		if (!existsByTeamName(teamName))
			return new ArrayList<>();
		return userTeamRoleRepository.findTeamMembersByTeamName(teamName);
	}

	@Override
	public List<String> getTeamNames() {
		return teamRepository.getTeamNames();
	}

	@Override
	public List<UserInfo> findMembers(Integer teamId) {
		return userTeamRoleRepository.findMembersByTeamId(teamId);
	}

	@Override
	public List<UserInfo> findSuperAdmins(){
		return userTeamRoleRepository.findSuperAdmins();
	}
}
