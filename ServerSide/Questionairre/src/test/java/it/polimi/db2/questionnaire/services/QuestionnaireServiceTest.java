package it.polimi.db2.questionnaire.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.UnauthorizedOperationException;
import it.polimi.db2.questionnaire.mappers.QuestionMapper;
import it.polimi.db2.questionnaire.mappers.QuestionnaireMapper;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Question;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;


@ExtendWith(MockitoExtension.class)
public class QuestionnaireServiceTest {

	@Mock private  UserService userService;
	@Mock private  ProductService productService;
	@Mock private  QuestionService questionService;
	@Mock private  LogService logService;
	@Mock private  QuestionnaireRepository questionnaireRepository;
	@Mock private  QuestionnaireMapper questionnaireMapper;
	@Mock private  QuestionMapper questionMapper;
	QuestionnaireService questionnaireService;
	
	@BeforeEach
	public void setup() {
		questionnaireService = new QuestionnaireService(userService, productService, questionService, logService, questionnaireRepository, questionnaireMapper, questionMapper);
	}
	
	@Test
	@DisplayName("Is it impossible to add a questionnaire in a day when exists another questionnaire")
	public void shouldDenyDuplicateDateQuestionnaireAdd() {
		//given 
		User user = User.builder()
				.roles("ROLE_ADMIN")
				.blocked(false)
				.email("tester@gmail.com")
				.id(1L)
				.password("testerPass")
				.username("tester")
				.build();
		Product product = Product.builder()
				.id(1L)
				.name("productTest")
				.photo(null)
				.build();
		Question q1 = Question.builder()
				.id(1L)
				.text("testQuestion1")
				.build();
		Question q2 = Question.builder()
				.id(1L)
				.text("testQuestion2")
				.build();
		QuestionnaireRequest request = QuestionnaireRequest.builder()
				.date(LocalDate.now().minusDays(5))
				.productId(product.getId())
				.title("testQuestionnaire")
				.questionsIds(List.of(q1,q2).stream().map(Question::getId).collect(Collectors.toList()))
				.build();
		
	
		
		Questionnaire existing = Questionnaire.builder()
				.creator(user)
				.date(request.getDate())
				.id(2L)
				.product(product)
				.questions(List.of(q1,q2))
				.title("testQuestionnaire2")
				.build();	

		
		//mocking
		Mockito.when(questionnaireRepository.findByDate(Mockito.any())).thenReturn(Optional.of(existing));
		
		//then
		assertThrows(DuplicateUniqueValueException.class,()->questionnaireService.addQuestionnaire(request));
	}
	
	@Test
	@DisplayName("Is it impossible to update a questionnaire in the past")
	public void shouldDenyPastQuestionnaireUpdate() {
		//given 
		User user = User.builder()
				.roles("ROLE_ADMIN")
				.blocked(false)
				.email("tester@gmail.com")
				.id(1L)
				.password("testerPass")
				.username("tester")
				.build();
		Product product = Product.builder()
				.id(1L)
				.name("productTest")
				.photo(null)
				.build();
		Question q1 = Question.builder()
				.id(1L)
				.text("testQuestion1")
				.build();
		Question q2 = Question.builder()
				.id(1L)
				.text("testQuestion2")
				.build();
		QuestionnaireRequest request = QuestionnaireRequest.builder()
				.date(LocalDate.now().plusDays(5))
				.productId(product.getId())
				.title("testQuestionnaire")
				.questionsIds(List.of(q1,q2).stream().map(Question::getId).collect(Collectors.toList()))
				.build();
		
		
		
		Questionnaire existing = Questionnaire.builder()
				.creator(user)
				.date(LocalDate.now().minusDays(5))
				.id(1L)
				.product(product)
				.questions(List.of(q1,q2))
				.title("testQuestionnaire2")
				.build();
		

		
		//mocking
		//Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(Optional.of(product));
		//Mockito.when(questionnaireMapper.toQuestionnaire(request, product, user, List.of(q1,q2))).thenReturn(questionnaire);
		//Mockito.when(questionService.getQuestion(q1.getId())).thenReturn(Optional.of(q1));
		//Mockito.when(questionService.getQuestion(q2.getId())).thenReturn(Optional.of(q2));
		//Mockito.when(userService.getLoggedUser()).thenReturn(Optional.of(user));
		//Mockito.when(questionnaireRepository.save(questionnaire)).thenReturn(saved);
		//Mockito.when(questionnaireRepository.findByDate(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(questionnaireRepository.findById(Mockito.any())).thenReturn(Optional.of(existing));
		
		
		
		//then
		assertThrows(UnauthorizedOperationException.class,()->questionnaireService.updateQuestionnaire(request, existing.getId()));
	}
	
	@Test
	@DisplayName("Is it impossible to update questionnaire of the day")
	public void shouldDenyQuestionnaireOfTheDayUpdate() {
		//given 
		User user = User.builder()
				.roles("ROLE_ADMIN")
				.blocked(false)
				.email("tester@gmail.com")
				.id(1L)
				.password("testerPass")
				.username("tester")
				.build();
		Product product = Product.builder()
				.id(1L)
				.name("productTest")
				.photo(null)
				.build();
		Question q1 = Question.builder()
				.id(1L)
				.text("testQuestion1")
				.build();
		Question q2 = Question.builder()
				.id(1L)
				.text("testQuestion2")
				.build();
		QuestionnaireRequest request = QuestionnaireRequest.builder()
				.date(LocalDate.now().plusDays(5))
				.productId(product.getId())
				.title("testQuestionnaire")
				.questionsIds(List.of(q1,q2).stream().map(Question::getId).collect(Collectors.toList()))
				.build();
		
		
		
		Questionnaire existing = Questionnaire.builder()
				.creator(user)
				.date(LocalDate.now())
				.id(1L)
				.product(product)
				.questions(List.of(q1,q2))
				.title("testQuestionnaire2")
				.build();
		

		
		//mocking
		//Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(Optional.of(product));
		//Mockito.when(questionnaireMapper.toQuestionnaire(request, product, user, List.of(q1,q2))).thenReturn(questionnaire);
		//Mockito.when(questionService.getQuestion(q1.getId())).thenReturn(Optional.of(q1));
		//Mockito.when(questionService.getQuestion(q2.getId())).thenReturn(Optional.of(q2));
		//Mockito.when(userService.getLoggedUser()).thenReturn(Optional.of(user));
		//Mockito.when(questionnaireRepository.save(questionnaire)).thenReturn(saved);
		//Mockito.when(questionnaireRepository.findByDate(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(questionnaireRepository.findById(Mockito.any())).thenReturn(Optional.of(existing));
		
		//then
		assertThrows(UnauthorizedOperationException.class,()->questionnaireService.updateQuestionnaire(request, existing.getId()));
	}
	
	
}
