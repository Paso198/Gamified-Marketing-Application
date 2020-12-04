package it.polimi.db2.questionnaire.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.UserRequest;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.mappers.UserMapper;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Transactional
	public void signup(UserRequest userRequest) {	
			userRepository.save(userMapper.mapToUser(userRequest, Role.USER, Boolean.FALSE, 0));
	}
}
