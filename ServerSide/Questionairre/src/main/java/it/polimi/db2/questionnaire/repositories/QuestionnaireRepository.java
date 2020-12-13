package it.polimi.db2.questionnaire.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.Questionnaire;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

	Optional <Questionnaire> findByDate(LocalDate date); //TODO Query?

}
