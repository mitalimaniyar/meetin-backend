package org.jeavio.meetin.backend.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.jeavio.meetin.backend.model.audit.UserDateAudit;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "users")
public class User extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547138919750970577L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 7, min = 7)
	@Column(unique = true, name = "emp_id")
	@NonNull
	private String empId;

	@NonNull
	@Size(max = 50)
	@Column(name = "first_name")
	private String firstName;

	@Size(max = 50)
	@Column(name = "last_name")
	private String lastName;

	@NonNull
	@Size(max = 70)
	@Column(unique = true)
	private String username;

	@NonNull
	@Size(max = 100)
	private String password;

	@NonNull
	@Email
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserTeamRole> userTeamRole = new LinkedHashSet<>();

	public User() {
	}

	public User(@Size(max = 7, min = 7) String empId, @Size(max = 50) String firstName, @Size(max = 50) String lastName,
			@Size(max = 70) String username, @Size(max = 100) String password, @Email String email) {
		super();
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

	public Set<UserTeamRole> getUserTeamRole() {
		return userTeamRole;
	}

	public void setUserTeamRole(Set<UserTeamRole> userTeamRole) {
		this.userTeamRole = userTeamRole;
	}

}
