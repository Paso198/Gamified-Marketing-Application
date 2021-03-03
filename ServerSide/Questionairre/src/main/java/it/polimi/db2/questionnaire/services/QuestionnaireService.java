package it.polimi.db2.questionnaire.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.AddQuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionnaireService {
	
	private final QuestionnaireRepository questionnaireRepository;
	//private final QuestionnaireMapper questionnaireMapper;
	
	public void addQuestionnaire(AddQuestionnaireRequest addQuestionnaireRequest) {
		//questionnaireRepository.save(questionnaireMapper.toQuestionnaire(addQuestionnaireRequest));
	}
	
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() { //.now() not tested
		Questionnaire questionnaire = questionnaireRepository.findByDate(LocalDate.now()).orElseThrow(/*TODO exception*/);
		//return questionnaireMapper.toQuestionnaireOfTheDayResponse(questionnaire);
		return null;
	}
	
	public Optional<Questionnaire> getQuestionnaire(Long id) {
		return questionnaireRepository.findById(id);
	}
	
	public void deleteQuestionnaire(Long id) {
		verifyOld(id);
		questionnaireRepository.deleteById(id);
	}
	
	public void verifyOld(Long id) {
		Questionnaire questionnaire = getQuestionnaire(id).orElseThrow(/*TODO exception*/);
		if(questionnaire.getDate().isBefore(LocalDate.now())) {
			//TODO exception
		}
	}
	
	//TODO: change with DTO
	public List<User> getUsersCancelled(Long questionnaireId){
		return questionnaireRepository.findById(questionnaireId)
				.get()	//TODO orElseThrow
				.getLogs()
				.stream()
				.filter((l)->l.getAction().equals(Action.CANCEL_QUESTIONNAIRE))
				.map(Log::getUser)
				.collect(Collectors.toList());
				
				
	}
}
