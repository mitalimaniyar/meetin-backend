package org.jeavio.meetin.backend.service;

import java.util.List;

public interface AdminService {

	public boolean promoteTeamAdmin(Integer teamId, String empId);

	public boolean promoteSuperAdmin(String empId);

	public boolean promoteTeamAdmin(Integer teamId, List<String> empIds);

}