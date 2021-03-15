package it.polimi.db2.questionnaire.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.polimi.db2.questionnaire.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p IN (SELECT q.product FROM Questionnaire q WHERE q.date = CURRENT_DATE)")
	Optional<Product> findProductOfTheDay();
}
