package it.polimi.db2.questionnaire.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireResponse;
import it.polimi.db2.questionnaire.services.QuestionnaireService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;

	@PostMapping("/admin/questionnaires")
	public ResponseEntity<Void> addQuestionnaire(@RequestBody @Valid QuestionnaireRequest request) {
		questionnaireService.addQuestionnaire(request);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/admin/questionnaires/past")
	public List<QuestionnaireResponse> getPastQuestionnaires(){
		return questionnaireService.getPastQuestionnaires();
	}
	
	@GetMapping("/admin/questionnaires/future")
	public List<QuestionnaireResponse> getFutureQuestionnaires(){
		return questionnaireService.getFutureQuestionnaires();
	}
	
	@GetMapping("/user/questionnaires")
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() {
		return questionnaireService.getQuestionnaireOfTheDay();
	}
	
	@PostMapping("/user/questionnaires/cancel")
	public ResponseEntity<Void> cancelQuestionnaireSubmission() {
		questionnaireService.cancelQuestionnaireSubmission();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/admin/questionnaires/{id}")
	public ResponseEntity<Void> updateQuestionnaire(@RequestBody @Valid QuestionnaireRequest request, @PathVariable Long id) {
		questionnaireService.updateQuestionnaire(request, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/questionnaires/{id}")
	public  ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long id) {
		questionnaireService.deleteQuestionnaire(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/admin/questionnaires/{id}/questions")
	public List<QuestionResponse> getQuestionsOfQuestionnaire(@PathVariable Long id){
		return questionnaireService.getQuestionsOfQuestionnaire(id);
	}
}
