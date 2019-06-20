package org.jeavio.meetin.backend.service;


import java.util.List;

import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dao.TeamRepository;
import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	RoomRepository roomRepository;

	public void promoteTeamAdmin(Integer teamId, String empId) {
		User user = userService.findUserByEmpId(empId);
		Team team = teamService.getTeamFromId(teamId);
		Role role = roleService.getTeamAdminRole();

		if (userTeamRoleRepository.existRecord(user, team, role)) {
			return;
		} else if (userTeamRoleRepository.existRecord(user, team, roleService.getTeamMemberRole())) {
			Role memberRole = roleService.getTeamMemberRole();
			UserTeamRole record = userTeamRoleRepository.findByUserAndTeamAndRole(user,team,memberRole);
			record.setRole(role);
			userTeamRoleRepository.save(record);
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, team, role));
		}
	}

	public void promoteSuperAdmin(String empId) {
		User user = userService.findUserByEmpId(empId);
		Role role = roleService.getSuperAdminRole();

		if (userTeamRoleRepository.existAdmin(user, role)) {
			return;
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, null, role));
		}
	}

	public void addTeam(String teamName) {
		Team team = new Team(teamName);
		teamRepository.save(team);
	}

	public void removeTeam(Integer teamId) {
		if (teamRepository.existsById(teamId))
			teamRepository.removeTeam(teamId);
	}

	public void updateTeamName(Integer teamId, String newTeamName) {
		Team team=teamRepository.findById(teamId).get();
		team.setTeamName(newTeamName);
		teamRepository.save(team);
	}

	public void addRoom(String roomName,Integer capacity,List<String> specifications) {
		
	}
	
	public void removeRoom(Integer roomId) {
		
	}
	
	public void updateRoom(String roomName,Integer capacity,List<String> specifications) {
//		Room updatedRoom = new Room()
	}
	
	public void addUser() {
		
	}
	
	public void removeUser() {
		
	}
}
