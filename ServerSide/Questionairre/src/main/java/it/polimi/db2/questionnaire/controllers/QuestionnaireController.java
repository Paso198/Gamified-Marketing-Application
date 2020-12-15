package it.polimi.db2.questionnaire.controllers;

import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.services.QuestionnaireService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class QuestionnaireController {
	
	private QuestionnaireService questionnaireService;
	
	

}
