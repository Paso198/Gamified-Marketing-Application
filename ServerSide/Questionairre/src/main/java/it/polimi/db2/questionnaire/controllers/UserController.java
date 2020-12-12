package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.UserRequest;
import it.polimi.db2.questionnaire.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping("/public/user/signup")
	public void signup(@Valid @RequestBody UserRequest userRequest){
		
		 userService.signup(userRequest);
		
	}
	

}
