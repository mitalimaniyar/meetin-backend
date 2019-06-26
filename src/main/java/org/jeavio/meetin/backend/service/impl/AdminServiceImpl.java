package org.jeavio.meetin.backend.service.impl;

import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dao.TeamRepository;
import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.model.Role;
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
		User user = userService.findByEmpId(empId);
		Team team = teamService.getTeamFromId(teamId);
		Role role = roleService.getTeamAdminRole();

		if(user==null)
			return;
		if (userTeamRoleRepository.existsByUserAndTeamAndRole(user, team, role)) {
			return;
		} else if (userTeamRoleRepository.existsByUserAndTeamAndRole(user, team, roleService.getTeamMemberRole())) {
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
		User user = userService.findByEmpId(empId);
		Role role = roleService.getSuperAdminRole();
		if(user==null)
			return;
		if (userTeamRoleRepository.existsAsAdmin(user, role)) {
			return;
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, null, role));
		}
	}
	
}
