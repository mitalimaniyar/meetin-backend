package org.jeavio.meetin.backend.security;

import java.util.List;
import java.util.stream.Collectors;

import org.jeavio.meetin.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authorizationManager")
public class AuthorizationManager {

	@Autowired
	EventService eventService;

	@Value("${app.authorization.enabled}")
	private boolean isAuthorizationEnabled;

	public boolean authorize(Authentication authentication, String module, String access) {
		if (!isAuthorizationEnabled) {
			return true;
		}
		if (authentication == null || authentication.getPrincipal() == null)
			return false;
		AppUser user = (AppUser) authentication.getPrincipal();
		List<String> authorities = user.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());
		if (module.equals("rooms")) {
			if (access.equals("VIEW_ACCESS"))
				return true;
			boolean otherAccess = access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
					|| access.equals("DELETE_ACCESS");
			if (otherAccess && authorities.contains("super_admin"))
				return true;
			else
				return false;
		}

		if (module.equals("admin")) {
			if (authorities.contains("super_admin"))
				return true;
			else
				return false;
		}

		if (module.startsWith("users")) {
			if (module.equals("users") && access.equals("VIEW_ACCESS"))
				return true;
			if (module.equals("users") && (access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
					|| access.equals("DELETE_ACCESS"))) {
				if (authorities.contains("super_admin"))
					return true;
				else
					return false;
			}
			if (module.equals("users_profile"))
				return true;
			if (module.equals("users_password") && access.equals("MODIFY_ACCESS"))
				return true;
		}

		if (module.startsWith("teams")) {

			if (module.equals("teams") && access.equals("VIEW_ACCESS"))
				return true;
			if (module.equals("teams") && (access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
					|| access.equals("DELETE_ACCESS"))) {
				if (authorities.contains("super_admin"))
					return true;
				else
					return false;
			}
			if (module.equals("teams_details"))
				return true;
		}
		if (module.startsWith("team_ops")) {
			String teamId = module.substring(module.length() - 1);
			String admin = teamId + "_team_admin";
			String member = teamId + "_team_member";
			if (module.startsWith("team_ops_members")) {
				if (authorities.contains(admin) || authorities.contains(member))
					return true;
				else
					return false;
			}
			if (authorities.contains(admin))
				return true;
			else
				return false;
		}

		if (module.startsWith("events")) {
			if (access.equals("CREATE_ACCESS") || access.equals("VIEW_ACCESS"))
				return true;
			if(access.equals("DELETE_ACCESS")) {
				String eventId = module.substring(8);
				String organizerId = eventService.getEventOrganizerId(eventId);
				if(authorities.contains("super_admin") || organizerId.equals(user.getEmpId()))
					return true;
				else
					return false;
			}
			if (access.equals("MODIFY_ACCESS")) {
				String eventId = module.substring(8);
				String organizerId = eventService.getEventOrganizerId(eventId);
				if(organizerId.equals(user.getEmpId()))
						return true;
				else
					return false;
			}

		}
		return false;
	}
}
