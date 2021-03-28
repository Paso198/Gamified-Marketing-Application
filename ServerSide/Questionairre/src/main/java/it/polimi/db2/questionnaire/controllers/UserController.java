package it.polimi.db2.questionnaire.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.UserRequest;
import it.polimi.db2.questionnaire.dto.responses.UserResponse;
import it.polimi.db2.questionnaire.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping("/public/signup")
	public void signup(@Valid @RequestBody UserRequest userRequest){	
		 userService.signup(userRequest);	
	}
	
	@GetMapping("/admin/questionnaires/{questionnaireId}/users/sent")
	public List<UserResponse> getUsersSent(@PathVariable Long questionnaireId){	
		 return userService.getUsersSent(questionnaireId);	
	}
	
	@GetMapping("/admin/questionnaires/{questionnaireId}/users/cancelled")
	public List<UserResponse> getUsersCancelled(@PathVariable Long questionnaireId){	
		 return userService.getUsersCancelled(questionnaireId);	
	}

}
