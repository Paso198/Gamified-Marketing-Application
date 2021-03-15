package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionController {
	
	@PostMapping("/admin/questions")
	public ResponseEntity<Void> addQuestion(@RequestBody @Valid QuestionRequest request){
		//TODO: service call
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
