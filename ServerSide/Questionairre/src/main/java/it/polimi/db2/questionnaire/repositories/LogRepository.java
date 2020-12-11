package it.polimi.db2.questionnaire.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
