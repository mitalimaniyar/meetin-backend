package org.jeavio.meetin.backend.dto;

public class UserInfo {

	private Integer id;
	private String empId;
	private String name;
	private String email;

	
	public UserInfo() { 	}

	public UserInfo(Integer id, String empId, String firstName, String lastName, String email) {
		this.id = id;
		this.empId = empId;
		if (lastName != null && !lastName.equals(""))
			this.name = firstName + " " + lastName;
		else
			this.name = firstName;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public String getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserInfo)) {
			return false;
		}
		UserInfo other = (UserInfo) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (empId == null) {
			if (other.empId != null) {
				return false;
			}
		} else if (!empId.equals(other.empId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"empId\":\"");
		builder.append(empId);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"email\":\"");
		builder.append(email);
		builder.append("\"}");
		return builder.toString();
	}

}
