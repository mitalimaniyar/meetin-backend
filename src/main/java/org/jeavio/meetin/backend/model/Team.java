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
@Table(name="teams")
public class Team extends UserDateAudit{

	/**
	 * 
	 */
	private static final long serialVersionUID = -811663712555750354L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NonNull
	@Column(name = "team_name")
	@Size(max = 50)
	private String teamName;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "team")
	private Set<UserTeamRole> userTeamRole=new LinkedHashSet<>();

	public Team() { 	}
	
	public Team(String teamName) {
		this.teamName=teamName;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Set<UserTeamRole> getUserTeamRole() {
		return userTeamRole;
	}

	public void setUserTeamRole(Set<UserTeamRole> userTeamRole) {
		this.userTeamRole = userTeamRole;
	}
}
