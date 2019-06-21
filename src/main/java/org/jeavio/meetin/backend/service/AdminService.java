package org.jeavio.meetin.backend.service;

import java.util.List;

public interface AdminService {

	void promoteTeamAdmin(Integer teamId, String empId);

	void promoteSuperAdmin(String empId);

	void addTeam(String teamName);

	void removeTeam(Integer teamId);

	void updateTeamName(Integer teamId, String newTeamName);

	void addRoom(String roomName, Integer capacity, List<String> specifications);

	void removeRoom(Integer roomId);

	void updateRoom(Integer roomId, String roomName, Integer capacity, List<String> specifications);

	void addUser();

	void removeUser(String empId);

}