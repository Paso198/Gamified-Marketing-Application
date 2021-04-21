package it.polimi.db2.questionnaire.services;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.polimi.db2.questionnaire.mappers.BadWordMapper;
import it.polimi.db2.questionnaire.model.BadWord;
import it.polimi.db2.questionnaire.repositories.BadWordRepository;

@ExtendWith(MockitoExtension.class)
public class BadWordServiceTest {

	@Mock private BadWordRepository badWordRepository;
	@Mock private BadWordMapper badWordMapper;
	BadWordService badWordService;
	@Captor private ArgumentCaptor<Boolean> badWordArgumentCaptor;
	
	@BeforeEach
	public void setup() {
		badWordService = new BadWordService(badWordRepository, badWordMapper);
	}
	
	@Test
	@DisplayName("Should Find A Bad Word")
	public void shouldFindABadWord() {
		//given 
		List<String> words = List.of("good", "bad", "ugly", "fuck");
		List<BadWord> badwords = List.of(new BadWord(Long.valueOf(1), "fuck"), new BadWord(Long.valueOf(1), "shit"), new BadWord(Long.valueOf(1), "bastard"));
		
		//mocking
		Mockito.when(badWordRepository.findAll()).thenReturn(badwords);
		
		//when
		Boolean containsBadWord = badWordService.containtsBadWord(words);
		
		//then
		Assertions.assertThat(containsBadWord).isTrue();
	}
}
