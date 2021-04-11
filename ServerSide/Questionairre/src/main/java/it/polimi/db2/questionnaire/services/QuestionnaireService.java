package it.polimi.db2.questionnaire.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireResponse;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.exceptions.QuestionNotFoundException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotAvailableException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnauthorizedOperationException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.mappers.QuestionMapper;
import it.polimi.db2.questionnaire.mappers.QuestionnaireMapper;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionnaireService {
	
	private final UserService userService;
	private final ProductService productService;
	private final QuestionService questionService;
	private final QuestionnaireRepository questionnaireRepository;
	private final QuestionnaireMapper questionnaireMapper;
	private final QuestionMapper questionMapper;
	
	@Transactional
	public void addQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		verifyDuplicate(questionnaireRequest.getDate());
		questionnaireRepository.save(questionnaireMapper.toQuestionnaire(questionnaireRequest,
				productService.getProduct(questionnaireRequest.getProductId()).orElseThrow(()->new ProductNotFoundException("invalid id", "product not found")), 
				userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in")), 
				questionnaireRequest.getQuestionsIds().stream().map((questionId) -> questionService.getQuestion(questionId).orElseThrow(
						()->new QuestionNotFoundException("Invalid id", "Question not found")))
				.collect(Collectors.toList())));
	}
	
	@Transactional
	public void updateQuestionnaire(QuestionnaireRequest questionnaireRequest, Long id) {
		Questionnaire toUpdate = questionnaireRepository.findById(id).orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found"));
		if(toUpdate.getDate().equals(LocalDate.now())) throw new UnauthorizedOperationException("Questionnaire of the day cannot be modified");
		verifyDuplicate(questionnaireRequest.getDate());
		toUpdate.setProduct(productService.getProduct(questionnaireRequest.getProductId()).orElseThrow(()->new ProductNotFoundException("invalid id", "product not found")));
		toUpdate.setDate(questionnaireRequest.getDate());
		toUpdate.setTitle(questionnaireRequest.getTitle());
		toUpdate.setQuestions(questionnaireRequest.getQuestionsIds().stream().map((questionId) -> questionService.getQuestion(questionId).orElseThrow(
						()->new QuestionNotFoundException("Invalid id", "Question not found")))
				.collect(Collectors.toList()));
	}
	
	@Transactional(readOnly = true)
	public QuestionnaireOfTheDayResponse getQuestionnaireOfTheDay() {
		Questionnaire questionnaire = questionnaireRepository.findQuestionnaireOfTheDay().orElseThrow(
				()->new QuestionnaireNotAvailableException("Questionnaire not available yet", "No Questionnaire found for current date"));
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
	
	@Transactional(readOnly = true)
	public Optional<Questionnaire> getQuestionnaire(Long id) {
		return questionnaireRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<QuestionResponse> getQuestionsOfQuestionnaire(Long id) {
		return questionMapper.toQuestionsResponse(getQuestionnaire(id).orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found")).getQuestions());
	}
	
	@Transactional(readOnly = true)
	private void verifyDuplicate(LocalDate date) {
		questionnaireRepository.findByDate(date).ifPresent(u->{throw new DuplicateUniqueValueException("Date", "It already exists a questionnaire with date "+date.toString());});
	}
	
	@Transactional(readOnly = true)
	private void verifyOld(Long id) {
		Questionnaire questionnaire = getQuestionnaire(id).orElseThrow(()->new QuestionnaireNotFoundException("Invalid id", "Questionnaire not found"));
		if(!questionnaire.getDate().isBefore(LocalDate.now())) {
			throw new UnauthorizedOperationException("Only past questionnaires can be updated or deleted");
		}
	}


}
