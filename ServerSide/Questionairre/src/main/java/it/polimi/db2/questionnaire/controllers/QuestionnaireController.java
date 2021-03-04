package it.polimi.db2.questionnaire.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.AddQuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireResponse;
import it.polimi.db2.questionnaire.services.QuestionnaireService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;

	@PostMapping("/admin/questionnaires")
	public ResponseEntity<Void> addQuestionnaire(@RequestBody @Valid AddQuestionnaireRequest request) {
		//TODO: service call
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/admin/questionnaires")
	public List<QuestionnaireResponse> getAllQuestionnaires(){
		return null;
		//TODO: service call
	}
	
	@GetMapping("/admin/questionnaires/{id}")
	public QuestionnaireResponse getQuestionnaire(@PathVariable Long id) {
		//TODO: service call
		return null;
		
	}
	
	@GetMapping("/user/questionnaires/questionnaire_of_the_day")
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() {
		//TODO: service call
				return null;
	}
	
	@DeleteMapping("/admin/questionnaires/{id}")
	public  ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	

}
