package org.jeavio.meetin.backend.dto;

public class MemberInfo extends UserInfo {

	private String team;

	public MemberInfo() {
	}

	public MemberInfo(Integer id, String empId, String firstName, String lastName, String email, String team) {
		super(id, empId, firstName, lastName, email);
		this.team = team;
	}

	public MemberInfo(UserInfo user,String team) {
		super(user.getId(),user.getEmpId(),user.getName(),null,user.getEmail());
		this.team = team;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
