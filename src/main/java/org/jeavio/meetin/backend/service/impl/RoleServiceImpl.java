package org.jeavio.meetin.backend.service.impl;

import org.jeavio.meetin.backend.dao.RoleRepository;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	static final String TEAM_ADMIN="team_admin";
	static final String TEAM_MEMBER="team_member";
	static final String SUPER_ADMIN="super_admin";
	
	@Override
	public Role getSuperAdminRole() {
		return roleRepository.findByRole(SUPER_ADMIN);
	}
	
	@Override
	public Role getTeamAdminRole() {
		return roleRepository.findByRole(TEAM_ADMIN);
	}
	
	@Override
	public Role getTeamMemberRole() {
		return roleRepository.findByRole(TEAM_MEMBER);
	}
}
