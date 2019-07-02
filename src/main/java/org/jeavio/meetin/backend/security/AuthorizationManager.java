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
		if (module.equals("ROOMS")) {
			if (access.equals("VIEW_ACCESS"))
				return true;
			boolean otherAccess = access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
					|| access.equals("DELETE_ACCESS");
			if (otherAccess && authorities.contains("super_admin"))
				return true;
			else
				return false;
		}

		if (module.equals("ADMIN")) {
			if (authorities.contains("super_admin"))
				return true;
			else
				return false;
		}

		if (module.startsWith("USERS")) {
			if (module.equals("USERS") && access.equals("VIEW_ACCESS"))
				return true;
			if (module.equals("USERS") && (access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
					|| access.equals("DELETE_ACCESS"))) {
				if (authorities.contains("super_admin"))
					return true;
				else
					return false;
			}
			if (module.equals("USERS_PASSWORD") && access.equals("MODIFY_ACCESS"))
				return true;
		}

		if (module.equals("TEAMS") && access.equals("VIEW_ACCESS"))
			return true;
		if (module.equals("TEAMS") && (access.equals("CREATE_ACCESS") || access.equals("MODIFY_ACCESS")
				|| access.equals("DELETE_ACCESS"))) {
			if (authorities.contains("super_admin"))
				return true;
			else
				return false;
		}

		if (module.startsWith("TEAM_OPS")) {
			String teamId = module.substring(module.length() - 1);
			String admin = teamId + "_team_admin";
			if (authorities.contains(admin))
				return true;
			else
				return false;
		}

		if (module.startsWith("EVENTS")) {
			if (access.equals("CREATE_ACCESS") || access.equals("VIEW_ACCESS"))
				return true;
			if (access.equals("DELETE_ACCESS")) {
				String eventId = module.substring(7);
				String organizerId = eventService.getEventOrganizerId(eventId);
				if (organizerId!=null && (authorities.contains("super_admin") || organizerId.equals(user.getEmpId())))
					return true;
				else
					return false;
			}
			if (access.equals("MODIFY_ACCESS")) {
				String eventId = module.substring(7);
				String organizerId = eventService.getEventOrganizerId(eventId);
				if (organizerId!=null && organizerId.equals(user.getEmpId()))
					return true;
				else
					return false;
			}

		}
		return false;
	}
}
