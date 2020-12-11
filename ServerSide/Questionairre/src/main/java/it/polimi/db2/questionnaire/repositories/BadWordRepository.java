package it.polimi.db2.questionnaire.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.BadWord;

@Repository
public interface BadWordRepository extends JpaRepository<BadWord, Long> {

}
