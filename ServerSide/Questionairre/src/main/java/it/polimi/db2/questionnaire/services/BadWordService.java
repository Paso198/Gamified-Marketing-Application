package it.polimi.db2.questionnaire.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.mappers.BadWordMapper;
import it.polimi.db2.questionnaire.model.BadWord;
import it.polimi.db2.questionnaire.repositories.BadWordRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BadWordService {
	
	private final BadWordRepository badWordRepository;
	private final BadWordMapper badWordMapper;
	
	public void addBadWord(String badWord) {
		badWordRepository.save(badWordMapper.mapToBadWord(badWord));
	}
	
	private Predicate<String> containsBadWordPredicate(){
		List<BadWord> badWords = badWordRepository.findAll();
		return  badWords.stream()
				.map(BadWord::getWord)
				.collect(Collectors.toSet())
				::contains;
	}
	
	public boolean containtsBadWord(List<String> words) {
		return words.stream().anyMatch(containsBadWordPredicate());
	}
}
