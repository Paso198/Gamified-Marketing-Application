package it.polimi.db2.questionnaire.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireResponse;
import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnauthorizedDeletionException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
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
				productService.getProduct(questionnaireRequest.getProductId()).orElseThrow(()->new ProductNotFoundException("invalid id", "product not found")), 
				userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in"))));
	}
	
	@Transactional(readOnly = true)
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() {
		Questionnaire questionnaire = questionnaireRepository.findQuestionnaireOfTheDay().orElseThrow(
				()->new QuestionnaireNotFoundException("No Questionnaire Today", "Questionnaire not found for current date"));
		return questionnaireMapper.toQuestionnaireOfTheDayResponse(questionnaire);
	}
	
	@Transactional(readOnly = true)
	public List<QuestionnaireResponse> getPastQuestionnaires() {
		return questionnaireMapper.toQuestionnairesResponse(questionnaireRepository.findPastQuestionnaires());
	}
	
	@Transactional(readOnly = true)
	public List<QuestionnaireResponse> getFutureQuestionnaires() {
		return questionnaireMapper.toQuestionnairesResponse(questionnaireRepository.findFutureQuestionnaires());
	}
	
	@Transactional
	public void deleteQuestionnaire(Long id) {
		verifyOld(id);
		questionnaireRepository.deleteById(id);
	}
	
	//TODO: change with DTO
	@Transactional(readOnly = true)
	public List<User> getUsersCancelled(Long questionnaireId){
		return questionnaireRepository.findById(questionnaireId)
				.orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found"))
				.getLogs()
				.stream()
				.filter((l)->l.getAction().equals(Action.CANCEL_QUESTIONNAIRE))
				.map(Log::getUser)
				.collect(Collectors.toList());
						
	}
	
	//TODO: change with DTO
	@Transactional(readOnly = true)
	public List<User> getUsersSubmitted(Long questionnaireId){
		return questionnaireRepository.findById(questionnaireId)
				.orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found"))
				.getLogs()
				.stream()
				.filter((l)->l.getAction().equals(Action.SEND_QUESTIONNAIRE))
				.map(Log::getUser)
				.collect(Collectors.toList());
						
	}
	
	@Transactional(readOnly = true)
	public Optional<Questionnaire> getQuestionnaire(Long id) {
		return questionnaireRepository.findById(id);
	}
	
	private void verifyDuplicate(LocalDate date) {
		questionnaireRepository.findByDate(date).ifPresent(u->{throw new DuplicateUniqueValueException("Date", "It already exists a questionnaire with date "+date.toString());});
	}
	
	private void verifyOld(Long id) {
		Questionnaire questionnaire = getQuestionnaire(id).orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found"));
		if(!questionnaire.getDate().isBefore(LocalDate.now())) {
			throw new UnauthorizedDeletionException("Only past questionnaires can be deleted");
		}
	}
}
