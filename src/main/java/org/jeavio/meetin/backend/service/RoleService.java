package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.model.Role;

public interface RoleService {

	Role getSuperAdminRole();

	Role getTeamAdminRole();

	Role getTeamMemberRole();

}