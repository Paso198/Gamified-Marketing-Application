package it.polimi.db2.questionnaire.repositories;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import it.polimi.db2.questionnaire.BaseJPATest;
import it.polimi.db2.questionnaire.enumerations.ExpertiseLevel;
import it.polimi.db2.questionnaire.enumerations.Gender;
import it.polimi.db2.questionnaire.model.Answer;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Question;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResponseRepositoryTests extends BaseJPATest{
	
	@Autowired
	ResponseRepository responseRepository;
	
	@Autowired
	TestEntityManager em;
	
	@Test
	 @DisplayName("Testing dependency injection")
	  void injectedComponentsAreNotNull(){
		 Assertions.assertThat(responseRepository).isNotNull();
		 Assertions.assertThat(em).isNotNull();
	  }
	
	@Test
	@DisplayName("Testing the trigger saving new response entity")
	public void testSave() {
			User admin = User.builder()
					.username("testerA")
					.password("passwordTest")
					.email("testA@gmail.com")
					.roles("ROLE_Admin")
					.blocked(false)
					.build();
			User u1 = User.builder()
					.username("tester")
					.password("passwordTest")
					.email("test@gmail.com")
					.roles("ROLE_USER")
					.blocked(false)
					.build();
			em.persist(admin);
			em.persist(u1);
			
			Product p1 = Product.builder()
					.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
					.name("test1")
					.build();
			Product p2 = Product.builder()
					.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
					.name("test2")
					.build();
			Product p3 = Product.builder()
					.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
					.name("test3")
					.build();
			em.persist(p1);
			em.persist(p2);
			em.persist(p3);
			Questionnaire q1 = Questionnaire.builder()
					.creator(admin)
					.date(LocalDate.now().minusDays(2))
					.title("test")
					.product(p1)
					.build();
			Questionnaire q2 = Questionnaire.builder()
					.creator(admin)
					.date(LocalDate.now())
					.title("test2")
					.product(p2)
					.build();
			Questionnaire q3 = Questionnaire.builder()
					.creator(admin)
					.date(LocalDate.now().minusDays(3))
					.title("test3")
					.product(p3)
					.build();
			
			em.persist(q1);
			em.persist(q2);
			em.persist(q3);
			
			Question s1 = Question.builder()
					.text("test1")
					.build();
			Question s2 = Question.builder()
					.text("test2")
					.build();
			Question s3 = Question.builder()
					.text("test3")
					.build();
			
			q1.addQuestion(s1);
			q1.addQuestion(s2);
			q2.addQuestion(s1);
			q2.addQuestion(s2);
			q2.addQuestion(s3);
			q3.addQuestion(s3);
			em.persist(s1);
			em.persist(s2);
			em.persist(s3);
			
			Answer a1 = Answer.builder()
					.question(s1)
					.text("test")
					.build();
			Answer a2 = Answer.builder()
					.question(s2)
					.text("test")
					.build();
			Response r1 = Response.builder()
					.age(13)
					//.answers(List.of(a1,a2))
					.questionnaire(q1)
					.user(u1)
					.build();
			Answer a3 = Answer.builder()
					.question(s1)
					.text("test")
					.build();
			Answer a4 = Answer.builder()
					.question(s2)
					.text("test")
					.build();
			Answer a5 = Answer.builder()
					.question(s3)
					.text("test")
					.build();
			Response r2 = Response.builder()
					.age(13)
					.expertiseLevel(ExpertiseLevel.HIGH)
					.gender(Gender.FEMALE)
				//	.answers(List.of(a3,a4,a5))
					.questionnaire(q2)
					.user(u1)
					.build();
			Answer a6 = Answer.builder()
					.question(s3)
					.text("test")
					.build();
			Response r3 = Response.builder()
				//	.answers(List.of(a6))
					.questionnaire(q3)
					.user(u1)
					.build();
			
			/*r1.setAnswers(List.of(a1,a2));
			r2.setAnswers(List.of(a3,a4,a5));
			r3.setAnswers(List.of(a6));*/
			r1.addAnswer(a1);
			r1.addAnswer(a2);
			r2.addAnswer(a3);
			r2.addAnswer(a4);
			r2.addAnswer(a5);
			r3.addAnswer(a6);
			
			em.persist(r1);
			em.persist(r2);
			em.persist(r3);
			
			
			Assertions.assertThat(r1.getPoints()).isEqualTo(4);
			Assertions.assertThat(r2.getPoints()).isEqualTo(9);
			Assertions.assertThat(r3.getPoints()).isEqualTo(1);
			
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
}
