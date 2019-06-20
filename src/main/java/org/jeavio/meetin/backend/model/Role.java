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
import javax.validation.constraints.Size;

import org.jeavio.meetin.backend.model.audit.UserDateAudit;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "roles")
public class Role extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -468762303825326284L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@Size(max = 50)
	@Column(unique = true)
	private String role;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserTeamRole> userTeamRole = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<UserTeamRole> getUserTeamRole() {
		return userTeamRole;
	}

	public void setUserTeamRole(Set<UserTeamRole> userTeamRole) {
		this.userTeamRole = userTeamRole;
	}
}
