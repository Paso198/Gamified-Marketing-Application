package it.polimi.db2.questionnaire.repositories;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import it.polimi.db2.questionnaire.BaseJPATest;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionnaireRepositoryTests extends BaseJPATest{
	
	@Autowired
	QuestionnaireRepository questionnaireRepository;
	
	@Autowired
	TestEntityManager em;
	
	@Test
	 @DisplayName("Testing dependency injection")
	  void injectedComponentsAreNotNull(){
		 Assertions.assertThat(questionnaireRepository).isNotNull();
		 Assertions.assertThat(em).isNotNull();
	  }
	
	@Test
	@DisplayName("Finding future questionnaires")
	public void testFutureQuestionnaires() {
		User admin = User.builder()
				.username("testerA")
				.password("passwordTest")
				.email("testA@gmail.com")
				.roles("ROLE_Admin")
				.blocked(false)
				.build();
		em.persist(admin);
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
		Product p4 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test3")
				.build();
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		em.persist(p4);
		
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
				.date(LocalDate.now().plusDays(3))
				.title("test3")
				.product(p3)
				.build();
		Questionnaire q4 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().plusDays(1))
				.title("test4")
				.product(p4)
				.build();
		em.persist(q1);
		em.persist(q2);
		em.persist(q3);
		em.persist(q4);
		
		List<Questionnaire> futures = questionnaireRepository.findFutureQuestionnaires();
		Assertions.assertThat(futures).contains(q3,q4).doesNotContain(q1,q2);
	}
	
	@Test
	@DisplayName("Finding past questionnaires")
	public void testPastQuestionnaires() {
		User admin = User.builder()
				.username("testerA")
				.password("passwordTest")
				.email("testA@gmail.com")
				.roles("ROLE_Admin")
				.blocked(false)
				.build();
		em.persist(admin);
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
		Product p4 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test3")
				.build();
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		em.persist(p4);
		
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
				.date(LocalDate.now().plusDays(3))
				.title("test3")
				.product(p3)
				.build();
		Questionnaire q4 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().plusDays(1))
				.title("test4")
				.product(p4)
				.build();
		em.persist(q1);
		em.persist(q2);
		em.persist(q3);
		em.persist(q4);
		
		List<Questionnaire> pasts = questionnaireRepository.findPastQuestionnaires();
		Assertions.assertThat(pasts).contains(q1).doesNotContain(q2,q3,q4);
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
