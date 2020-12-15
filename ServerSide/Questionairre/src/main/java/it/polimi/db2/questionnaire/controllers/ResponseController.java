package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ResponseController {
	
	@PostMapping("user/responses/submit")
	public ResponseEntity<Void> submitResponse(@RequestBody @Valid ResponseRequest request){
		//TODO: service call
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
