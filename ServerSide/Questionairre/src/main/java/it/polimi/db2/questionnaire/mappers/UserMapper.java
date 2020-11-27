package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.polimi.db2.questionnaire.dto.UserRequest;
import it.polimi.db2.questionnaire.enumerations.Role;
import it.polimi.db2.questionnaire.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Mapping(target="id", ignore=true)
	@Mapping(target="username", expression="java(userRequest.getUsername())")
	@Mapping(target="email", expression="java(userRequest.getEmail())")
	@Mapping(target="password", expression="java(passwordEncoder.encode(userRequest.getPassword()))")
	@Mapping(target="points", source="points")
	@Mapping(target="blocked", source="blocked")
	@Mapping(target="roles", expression="java(getRole(role))")
	public abstract User mapToUser(UserRequest userRequest, Role role, Boolean blocked, Integer points);
	
	String getRole(Role role) {
		return "ROLE_"+role.name();
	}
}
