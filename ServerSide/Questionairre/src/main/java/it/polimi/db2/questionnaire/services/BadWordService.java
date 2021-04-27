package it.polimi.db2.questionnaire.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.model.BadWord;
import it.polimi.db2.questionnaire.repositories.BadWordRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BadWordService {
	
	private final BadWordRepository badWordRepository;
	
	
	@Transactional(readOnly=true)
	private Predicate<String> containsBadWordPredicate(){
		List<BadWord> badWords = badWordRepository.findAll();
		return  badWords.stream()
				.map(BadWord::getWord)
				.map(String::toLowerCase)
				.collect(Collectors.toSet())
				::contains;
	}
	
	@Transactional(readOnly=true)
	public boolean containtsBadWord(List<String> words) {
		return words.stream().anyMatch(containsBadWordPredicate());
	}
}
