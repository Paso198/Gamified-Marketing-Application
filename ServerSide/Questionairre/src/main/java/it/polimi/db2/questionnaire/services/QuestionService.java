package it.polimi.db2.questionnaire.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.mappers.QuestionMapper;
import it.polimi.db2.questionnaire.model.Question;
import it.polimi.db2.questionnaire.repositories.QuestionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	
	@Transactional
	public void addQuestion(QuestionRequest questionRequest) {
		verifyDuplicate(questionRequest.getText());
		questionRepository.save(questionMapper.toQuestion(questionRequest));
	}
	
	@Transactional(readOnly = true)
	public List<QuestionResponse> getAllQuestions() {
		return questionMapper.toQuestionsResponse(questionRepository.findAll());
	}
	
	@Transactional(readOnly = true)
	public Optional<Question> getQuestion(Long id) {
		return questionRepository.findById(id);	
	}
	
	@Transactional(readOnly = true)
	private void verifyDuplicate(String text) {
		questionRepository.findByText(text).ifPresent(u->{throw new DuplicateUniqueValueException("Text", "It already exists a question with text "+text);});
	}

}
