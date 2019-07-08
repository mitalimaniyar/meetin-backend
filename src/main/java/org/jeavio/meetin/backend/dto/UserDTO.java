package org.jeavio.meetin.backend.dto;

import org.springframework.lang.NonNull;

public class UserDTO {

	@NonNull
	private Integer id;
	@NonNull
	private String empId;
	@NonNull
	private String firstName;
	private String lastName;
	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String email;
	
	public UserDTO() {	   }
	
	public UserDTO(String empId, String firstName, String lastName, String username, String password, String email) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public UserDTO(Integer id, String empId, String firstName, String lastName, String username, String password,
			String email) {
		super();
		this.id = id;
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"empId\":\"");
		builder.append(empId);
		builder.append("\", \"firstName\":\"");
		builder.append(firstName);
		builder.append("\", \"lastName\":\"");
		builder.append(lastName);
		builder.append("\", \"username\":\"");
		builder.append(username);
		builder.append("\", \"password\":\"");
		builder.append(password);
		builder.append("\", \"email\":\"");
		builder.append(email);
		builder.append("\"}");
		return builder.toString();
	}
	
}
