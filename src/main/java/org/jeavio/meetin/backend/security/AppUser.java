package org.jeavio.meetin.backend.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Integer id;
	private final String empId;
	private final String name;
	private final String username;
	private final String password;
	private final String email;
	private final Collection<? extends GrantedAuthority> authorities;

	public AppUser(Integer id,String userId, String firstname, String lastname, String username, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.empId=userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		if (lastname == null || lastname.equals(""))
			this.name = firstname;
		else
			this.name = firstname + " " + lastname;

	}

	public static AppUser create(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<UserTeamRole> userRoles = user.getUserTeamRole();
		for(UserTeamRole userRole:userRoles) {
			String role =null;
			if(userRole.getRole().getRole().equals("super_admin"))
				role = userRole.getRole().getRole();
			else if(userRole.getTeam()!=null)
				role = userRole.getTeam().getId()+"_"+userRole.getRole().getRole();
			if(role!=null)
				authorities.add(new SimpleGrantedAuthority(role));
		}
		return new AppUser(user.getId(),user.getEmpId(), user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getPassword(), user.getEmail(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
