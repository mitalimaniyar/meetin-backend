package org.jeavio.meetin.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class TeamDetails {

	private Integer teamId;
	private String teamName;
	private List<UserInfo> teamMembers;
	private List<UserInfo> teamAdmins;
	private Boolean isAdmin;
	
	public TeamDetails() { }
	
	
	
	public TeamDetails(Integer teamId, String teamName) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamMembers=new ArrayList<UserInfo>();
		this.teamAdmins=new ArrayList<UserInfo>();
		this.isAdmin = false;
	}



	public Integer getTeamId() {
		return teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public List<UserInfo> getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(List<UserInfo> teamMembers) {
		this.teamMembers = teamMembers;
	}
	public List<UserInfo> getTeamAdmins() {
		return teamAdmins;
	}
	
	public void setTeamAdmins(List<UserInfo> teamAdmins) {
		this.teamAdmins = teamAdmins;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	
}
