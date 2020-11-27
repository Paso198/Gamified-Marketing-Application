package it.polimi.db2.questionnaire.services;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import it.polimi.db2.questionnaire.dto.UserRequest;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.mappers.UserMapper;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public void signup(UserRequest userRequest) {
		try{
			
			User u = userMapper.mapToUser(userRequest, Role.USER, Boolean.FALSE, 0);
			userRepository.save(u);
		}catch(PersistenceException e) {
			System.out.println(e.getMessage());
		}
}}
