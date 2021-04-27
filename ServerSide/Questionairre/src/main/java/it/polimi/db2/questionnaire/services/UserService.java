package it.polimi.db2.questionnaire.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.UserRequest;
import it.polimi.db2.questionnaire.dto.responses.UserResponse;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.mappers.UserMapper;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import it.polimi.db2.questionnaire.specifications.UserSpecs;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	@Transactional
	public void blockLogged() {
		User userToBlock = getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in"));
		userToBlock.setBlocked(Boolean.TRUE);
	}

	@Transactional
	public void signup(UserRequest userRequest) {	
			User user =userMapper.toUser(userRequest, Role.USER, Boolean.FALSE, 0);
			verifyDuplicate(user);
			try{
				userRepository.save(user);
			}catch(PersistenceException ex) {
				throw new DuplicateUniqueValueException("Email or User", "It already exists an user with email "+user.getEmail()+" or username "+user.getUsername());
			}
	}
	
	@Transactional(readOnly = true)
	private void verifyDuplicate(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(u->{throw new DuplicateUniqueValueException("Email", "It already exists an user with email "+user.getEmail());});
		userRepository.findByUsername(user.getUsername()).ifPresent(u->{throw new DuplicateUniqueValueException("Username", "It already exists an user with username "+user.getUsername());});
	}
	
	@Transactional(readOnly = true)
	public Optional<User> getLoggedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal != null) {
			Optional<User> user = userRepository.findByUsername((String)principal);
			return user;
		}
		return Optional.empty();
	}

	@Transactional(readOnly = true)
	public List<UserResponse> getUsersSent(Long questionnaireId) {
		return userMapper.toUsersResponse(userRepository.findAll(UserSpecs.usersSentQuestionaire(questionnaireId)));	
	}
	
	@Transactional(readOnly = true)
	public List<UserResponse> getUsersCancelled(Long questionnaireId) {
		return userMapper.toUsersResponse(userRepository.findAll(UserSpecs.usersCancelledQuestionaire(questionnaireId)));	
	}
}
