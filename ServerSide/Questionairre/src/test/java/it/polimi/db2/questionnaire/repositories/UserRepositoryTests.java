package it.polimi.db2.questionnaire.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import it.polimi.db2.questionnaire.BaseJPATest;
import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.specifications.UserSpecs;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests extends BaseJPATest{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager em;
	
	@Test
	 @DisplayName("Testing dependency injection")
	  void injectedComponentsAreNotNull(){
		 Assertions.assertThat(userRepository).isNotNull();
		 Assertions.assertThat(em).isNotNull();
	  }
	
	@Test
	@DisplayName("Saving new user test")
	void testSave() {
		User expectedUser = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		User actualUser = userRepository.save(expectedUser);
		Assertions.assertThat(actualUser)
					.usingRecursiveComparison()
					.ignoringFields("id")
					.isEqualTo(expectedUser);
	}
	
	@Test
	@DisplayName("Saving user with duplicate mail test")
	void testDuplicateMail() {
		User user1 = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(user1);
		User user2 = User.builder()
				.username("tester2")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		assertThrows(DataIntegrityViolationException.class, ()->userRepository.save(user2));
	}
	
	@Test
	@DisplayName("Saving user with duplicate username test")
	void testDuplicateUsername() {
		User user1 = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test2@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(user1);
		User user2 = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		assertThrows(DataIntegrityViolationException.class, ()->userRepository.save(user2));
	}
	
	
	@Test
	@DisplayName("Finding by mail test")
	void testFindByMail() {
		User expectedUser = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(expectedUser);
		Optional<User> actualUser = userRepository.findByEmail(expectedUser.getEmail());
		Assertions.assertThat(actualUser).isNotEmpty();
		Assertions.assertThat(actualUser.get())
					.usingRecursiveComparison()
					.isEqualTo(expectedUser);
	}
	
	@Test
	@DisplayName("Finding by username test")
	void testFindByUsername() {
		User expectedUser = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(expectedUser);
		Optional<User> actualUser = userRepository.findByUsername(expectedUser.getUsername());
		Assertions.assertThat(actualUser).isNotEmpty();
		Assertions.assertThat(actualUser.get())
					.usingRecursiveComparison()
					.isEqualTo(expectedUser);
	}
	
	@Test
	@DisplayName("Finding by id test")
	void testFindById() {
		User expectedUser = User.builder()
				.username("tester")
				.password("passwordTest")
				.email("test@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(expectedUser);
		Optional<User> actualUser = userRepository.findById(expectedUser.getId());
		Assertions.assertThat(actualUser).isNotEmpty();
		Assertions.assertThat(actualUser.get())
					.usingRecursiveComparison()
					.isEqualTo(expectedUser);
	}
	
	@Test
	@DisplayName("Finding users who sent a questionnaire")
	void testUsersSentQuestionnaire() {
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
		User u2 = User.builder()
				.username("tester1")
				.password("passwordTest")
				.email("test1@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		User u3 = User.builder()
				.username("tester2")
				.password("passwordTest")
				.email("test2@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		User u4 = User.builder()
				.username("tester3")
				.password("passwordTest")
				.email("test3@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(u1);
		em.persist(u2);
		em.persist(u3);
		em.persist(u4);
		em.persist(admin);
		Product p1 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test1")
				.build();
		Product p2 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test2")
				.build();
		em.persist(p1);
		em.persist(p2);
		
		Questionnaire q1 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().minusDays(2))
				.title("test")
				.product(p1)
				.build();
		Questionnaire q2 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().minusDays(3))
				.title("test2")
				.product(p2)
				.build();
		em.persist(q1);
		em.persist(q2);
		
		Response r1 = Response.builder()
				.user(u1)
				.questionnaire(q1)
				.points(8)
				.build();
		Response r2 = Response.builder()
				.user(u2)
				.questionnaire(q2)
				.points(8)
				.build();
		Response r3 = Response.builder()
				.user(u4)
				.questionnaire(q1)
				.points(10)
				.build();
		Response r4 = Response.builder()
				.user(u3)
				.questionnaire(q1)
				.points(6)
				.build();
		em.persist(r1);
		em.persist(r2);
		em.persist(r3);
		em.persist(r4);
		
		List<User> send = userRepository.findAll(UserSpecs.usersSentQuestionaire(q1.getId()));
		Assertions.assertThat(send).contains(u4, u1, u3).doesNotContain(u2);
	}
	
	@Test
	@DisplayName("Finding users who cancelled a questionnaire")
	void testUsersCancelledQuestionnaire() {
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
		User u2 = User.builder()
				.username("tester1")
				.password("passwordTest")
				.email("test1@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		User u3 = User.builder()
				.username("tester2")
				.password("passwordTest")
				.email("test2@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		User u4 = User.builder()
				.username("tester3")
				.password("passwordTest")
				.email("test3@gmail.com")
				.roles("ROLE_USER")
				.blocked(false)
				.build();
		em.persist(u1);
		em.persist(u2);
		em.persist(u3);
		em.persist(u4);
		em.persist(admin);
		Product p1 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test1")
				.build();
		Product p2 = Product.builder()
				.photo(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"))
				.name("test2")
				.build();
		em.persist(p1);
		em.persist(p2);
		
		Questionnaire q1 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().minusDays(2))
				.title("test")
				.product(p1)
				.build();
		Questionnaire q2 = Questionnaire.builder()
				.creator(admin)
				.date(LocalDate.now().minusDays(3))
				.title("test2")
				.product(p2)
				.build();
		em.persist(q1);
		em.persist(q2);
				
		Log l1 = Log.builder()
				.action(Action.SEND_QUESTIONNAIRE)
				.questionnaire(q1)
				.user(u1)
				.build();
		Log l2 = Log.builder()
				.action(Action.CANCEL_QUESTIONNAIRE)
				.questionnaire(q1)
				.user(u2)
				.build();
		Log l3 = Log.builder()
				.action(Action.SEND_QUESTIONNAIRE)
				.questionnaire(q2)
				.user(u3)
				.build();
		Log l4 = Log.builder()
				.action(Action.SEND_QUESTIONNAIRE)
				.questionnaire(q2)
				.user(u4)
				.build();
		Log l5 = Log.builder()
				.action(Action.SEND_QUESTIONNAIRE)
				.questionnaire(q1)
				.user(u4)
				.build();
		em.persist(l1);
		em.persist(l2);
		em.persist(l3);
		em.persist(l4);
		em.persist(l5);
		
		List<User> send = userRepository.findAll(UserSpecs.usersCancelledQuestionaire(q1.getId()));
		List<User> send2 = userRepository.findAll(UserSpecs.usersCancelledQuestionaire(q2.getId()));
		Assertions.assertThat(send).contains(u2).doesNotContain(u1, u3, u4);
		Assertions.assertThat(send2).isEmpty();
		Assertions.assertThat(send.get(0).getUsername()).isEqualTo("tester1");
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
