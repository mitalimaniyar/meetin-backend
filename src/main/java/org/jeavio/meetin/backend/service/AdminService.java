package org.jeavio.meetin.backend.service;

public interface AdminService {

	void promoteTeamAdmin(Integer teamId, String empId);

	void promoteSuperAdmin(String empId);

}