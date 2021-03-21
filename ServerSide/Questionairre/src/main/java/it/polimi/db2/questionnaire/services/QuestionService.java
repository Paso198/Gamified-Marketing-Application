package it.polimi.db2.questionnaire.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import it.polimi.db2.questionnaire.model.Question;
import it.polimi.db2.questionnaire.repositories.QuestionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	//private final QuestionMapper questionMapper;
	
	@Transactional(readOnly = true)
	public Optional<Question> getQuestion(Long id) {
		return questionRepository.findById(id);	
	}
	
	public void addQuestion(QuestionRequest questionRequest) {
		//questionRepository.save(questionMapper.toQuestion(addQuestionRequest));
	}
	
	public void deleteQuestion(Long id) {
		questionRepository.deleteById(id);
	}
	
	
}
