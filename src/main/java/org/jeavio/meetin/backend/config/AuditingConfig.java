package org.jeavio.meetin.backend.config;

import java.util.Optional;

import org.jeavio.meetin.backend.security.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@ComponentScan
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}

class SpringSecurityAuditAwareImpl implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        AppUser appUser = (AppUser) authentication.getPrincipal();

        return Optional.ofNullable(appUser.getId());
    }
}
 