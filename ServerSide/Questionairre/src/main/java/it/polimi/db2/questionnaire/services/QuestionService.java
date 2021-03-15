package it.polimi.db2.questionnaire.services;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import it.polimi.db2.questionnaire.repositories.QuestionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	//private final QuestionMapper questionMapper;
	
	public void addQuestion(QuestionRequest questionRequest) {
		//questionRepository.save(questionMapper.toQuestion(addQuestionRequest));
	}
	
	public void deleteQuestion(Long id) {
		questionRepository.deleteById(id);
	}
}
