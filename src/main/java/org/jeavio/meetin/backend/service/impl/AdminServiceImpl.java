package org.jeavio.meetin.backend.service.impl;

import java.util.List;

import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dao.TeamRepository;
import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Room;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.jeavio.meetin.backend.service.AdminService;
import org.jeavio.meetin.backend.service.RoleService;
import org.jeavio.meetin.backend.service.TeamService;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

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

	@Override
	public void promoteTeamAdmin(Integer teamId, String empId) {
		User user = userService.findUserByEmpId(empId);
		Team team = teamService.getTeamFromId(teamId);
		Role role = roleService.getTeamAdminRole();

		if (userTeamRoleRepository.existRecord(user, team, role)) {
			return;
		} else if (userTeamRoleRepository.existRecord(user, team, roleService.getTeamMemberRole())) {
			Role memberRole = roleService.getTeamMemberRole();
			UserTeamRole record = userTeamRoleRepository.findByUserAndTeamAndRole(user, team, memberRole);
			record.setRole(role);
			userTeamRoleRepository.save(record);
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, team, role));
		}
	}

	@Override
	public void promoteSuperAdmin(String empId) {
		User user = userService.findUserByEmpId(empId);
		Role role = roleService.getSuperAdminRole();

		if (userTeamRoleRepository.existAdmin(user, role)) {
			return;
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, null, role));
		}
	}

	@Override
	public void addTeam(String teamName) {
		Team team = new Team(teamName);
		teamRepository.save(team);
	}

	@Override
	public void removeTeam(Integer teamId) {
		if (teamRepository.existsById(teamId))
			teamRepository.deleteById(teamId);;
	}

	@Override
	public void updateTeamName(Integer teamId, String newTeamName) {
		Team team = teamRepository.findById(teamId).get();
		team.setTeamName(newTeamName);
		teamRepository.save(team);
	}

	@Override
	public void addRoom(String roomName, Integer capacity, List<String> specifications) {
		if (roomRepository.existsByRoomName(roomName))
			return;
		String specification;
		if (specifications != null && !specifications.isEmpty()) {
			specification = String.join(",", specifications);
			roomRepository.save(new Room(roomName, capacity, specification));
		} else {
			roomRepository.save(new Room(roomName, capacity));
		}
	}

	@Override
	public void removeRoom(Integer roomId) {
		if (teamRepository.existsById(roomId))
			roomRepository.deleteById(roomId);
	}

	@Override
	public void updateRoom(Integer roomId, String roomName, Integer capacity, List<String> specifications) {
		Room room = roomRepository.findById(roomId).get();
		if (roomRepository.existsByRoomName(roomName))
			return;
		String specification = null;
		if (specifications != null && !specifications.isEmpty()) {
			specification = String.join(",", specifications);
			room.setSpecifications(specification);
		}
		room.setRoomName(roomName);
		room.setCapacity(capacity);

		roomRepository.save(room);
	}

	@Override
	public void addUser() {

	}

	@Override
	public void removeUser(String empId) {
		Integer userId = userService.findIdByEmpId(empId);
		userRepository.deleteById(userId);
	}

	
}
