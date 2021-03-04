package it.polimi.db2.questionnaire.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	Optional <User> findByUsername(String username);
	Optional <User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.id IN (SELECT l.user FROM Log l WHERE l.questionnaire = ?1 AND l.action = 'SEND_QUESTIONNAIRE')" )
	List<User> findBySubmitting(Long questionnaireId);
	
}
