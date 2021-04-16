package it.polimi.db2.questionnaire.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.config.ApplicationUser;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final LogService logService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		if(!user.getBlocked())
			logService.logLogin(user);
		return new ApplicationUser(user);
	}
}
