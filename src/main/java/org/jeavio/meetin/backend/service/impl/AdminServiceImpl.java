package org.jeavio.meetin.backend.service.impl;

import java.util.List;

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

	@Override
	public boolean promoteTeamAdmin(Integer teamId, List<String> empIds) {
		if(!teamService.existsByTeamId(teamId))
			return false;
		for(String empId:empIds) {
			if(!userService.existsByEmpId(empId))
				return false;
			if(!promoteTeamAdmin(teamId,empId))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean promoteTeamAdmin(Integer teamId, String empId) {
		if(!userService.existsByEmpId(empId) || !teamService.existsByTeamId(teamId))
			return false;
		User user = userService.findByEmpId(empId);
		Team team = teamService.getTeamFromId(teamId);
		Role role = roleService.getTeamAdminRole();

		if(user==null)
			return false;
		if (userTeamRoleRepository.existsByUserAndTeamAndRole(user, team, role)) {
			return true;
		} else if (userTeamRoleRepository.existsByUserAndTeamAndRole(user, team, roleService.getTeamMemberRole())) {
			Role memberRole = roleService.getTeamMemberRole();
			UserTeamRole record = userTeamRoleRepository.findByUserAndTeamAndRole(user, team, memberRole);
			record.setRole(role);
			userTeamRoleRepository.save(record);
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, team, role));
		}
		return true;
	}

	@Override
	public boolean promoteSuperAdmin(String empId) {
		User user = userService.findByEmpId(empId);
		Role role = roleService.getSuperAdminRole();
		if(user==null)
			return false;
		if (userTeamRoleRepository.existsAsAdmin(user, role)) {
			return true;
		} else {
			userTeamRoleRepository.save(new UserTeamRole(user, null, role));
		}
		return true;
	}

	
}
