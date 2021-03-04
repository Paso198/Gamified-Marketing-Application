package it.polimi.db2.questionnaire.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
	Optional<Response >findByQuestionnaire_IdAndUser_Id(Long questionnaireId, Long userId);
}
