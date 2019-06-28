package org.jeavio.meetin.backend.service;

public interface AdminService {

	public boolean promoteTeamAdmin(Integer teamId, String empId);

	public boolean promoteSuperAdmin(String empId);

}