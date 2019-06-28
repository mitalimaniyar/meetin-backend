package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.model.Role;

public interface RoleService {

	public Role getSuperAdminRole();

	public Role getTeamAdminRole();

	public Role getTeamMemberRole();

}