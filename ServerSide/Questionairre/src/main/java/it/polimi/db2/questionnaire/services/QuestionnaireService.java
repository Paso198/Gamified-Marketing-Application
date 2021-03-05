package it.polimi.db2.questionnaire.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.mappers.QuestionnaireMapper;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionnaireService {
	
	private final UserService userService;
	private final ProductService productService;
	private final QuestionnaireRepository questionnaireRepository;
	private final QuestionnaireMapper questionnaireMapper;
	
	@Transactional
	public void addQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		verifyDuplicate(questionnaireRequest.getDate());
		questionnaireRepository.save(questionnaireMapper.toQuestionnaire(questionnaireRequest,
				productService.findProduct(questionnaireRequest.getProductId()), 
				userService.getLoggedUser().orElseThrow(/*TODO unlogged exception*/)));
	}
	
	@Transactional(readOnly = true)
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() {
		Questionnaire questionnaire = questionnaireRepository.findByDate(LocalDate.now()).orElseThrow(/*TODO exception*/);
		return questionnaireMapper.toQuestionnaireOfTheDayResponse(questionnaire);
	}
	
	/*@Transactional(readOnly = true)
	public List<Questionnaire> getPastQuestionnaires() {
		return questionnaireRepository.;
	}*/
	
	/*@Transactional(readOnly = true)
	public List<Questionnaire> getFutureQuestionnaires() {
		return questionnaireRepository.;
	}*/
	
	@Transactional
	public void deleteQuestionnaire(Long id) {
		verifyOld(id);
		questionnaireRepository.deleteById(id);
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
	
	private void verifyDuplicate(LocalDate date) {
		questionnaireRepository.findByDate(date).ifPresent(u->{throw new DuplicateUniqueValueException("Date", "It already exists a questionnaire with date "+date.toString());});
	}
	
	private void verifyOld(Long id) {
		Questionnaire questionnaire = getQuestionnaire(id).orElseThrow(/*TODO exception*/);
		if(questionnaire.getDate().isBefore(LocalDate.now())) {
			//TODO exception
		}
	}
	
	@Transactional(readOnly = true)
	private Optional<Questionnaire> getQuestionnaire(Long id) {
		return questionnaireRepository.findById(id);
	}
}
