package it.polimi.db2.questionnaire.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.services.QuestionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionController {
	
	private QuestionService questionService;
	
	@PostMapping("/admin/questions")
	public ResponseEntity<Void> addQuestion(@RequestBody @Valid QuestionRequest request){
		questionService.addQuestion(request);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/admin/questions")
	public List<QuestionResponse> getAllQuestions(){
		//TODO: service call
		return null;
	}
}
