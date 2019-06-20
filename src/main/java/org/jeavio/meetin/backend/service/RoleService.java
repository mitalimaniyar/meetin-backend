package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dao.RoleRepository;
import org.jeavio.meetin.backend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	private static final String TEAM_ADMIN="team_admin";
	private static final String TEAM_MEMBER="team_member";
	private static final String SUPER_ADMIN="super_admin";
	
	public Role getSuperAdminRole() {
		return roleRepository.findByRole(SUPER_ADMIN);
	}
	
	public Role getTeamAdminRole() {
		return roleRepository.findByRole(TEAM_ADMIN);
	}
	
	public Role getTeamMemberRole() {
		return roleRepository.findByRole(TEAM_MEMBER);
	}
	
}
