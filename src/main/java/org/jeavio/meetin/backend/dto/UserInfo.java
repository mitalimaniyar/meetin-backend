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
}
