package it.polimi.db2.questionnaire.services;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.UserRequest;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.mappers.UserMapper;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Transactional
	public void signup(UserRequest userRequest) {	
			User user =userMapper.mapToUser(userRequest, Role.USER, Boolean.FALSE, 0);
			verifyDuplicate(user);
			try{
				userRepository.save(user);
			}catch(PersistenceException ex) {
				throw new DuplicateUniqueValueException("Email or User", "It already exists an user with email "+user.getEmail()+" or username "+user.getUsername());
			}
	}
	
	@Transactional
	private void verifyDuplicate(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(u->{throw new DuplicateUniqueValueException("Email", "It already exists an user with email "+user.getEmail());});
		userRepository.findByUsername(user.getUsername()).ifPresent(u->{throw new DuplicateUniqueValueException("Username", "It already exists an user with username "+user.getUsername());});
	}
}
