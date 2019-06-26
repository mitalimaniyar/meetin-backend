package org.jeavio.meetin.backend.dto;

public class MemberInfo extends UserInfo {

	private String team;

	public MemberInfo() {
	}

	public MemberInfo(Integer id, String empId, String firstName, String lastName, String email, String team) {
		super(id, empId, firstName, lastName, email);
		this.team = team;
	}

	public MemberInfo(UserInfo user, String team) {
		super(user.getId(), user.getEmpId(), user.getName(), null, user.getEmail());
		this.team = team;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof MemberInfo)) {
			return false;
		}
		MemberInfo other = (MemberInfo) obj;
		if (team == null && other.team != null) {
			return false;
		} else
			return true;
	}

}
