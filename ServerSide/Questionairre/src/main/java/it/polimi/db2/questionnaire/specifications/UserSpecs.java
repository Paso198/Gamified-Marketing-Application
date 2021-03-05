package it.polimi.db2.questionnaire.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.User;

public class UserSpecs {
	
	//@Query("SELECT u FROM User u WHERE u.id IN (SELECT l.user FROM Log l WHERE l.questionnaire = ?1 AND l.action = 'SEND_QUESTIONNAIRE')" )
	public static Specification<User> usersSentQuestionaire(Long questionnaireId){
		return new Specification<User>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
		
				/*Subquery<Log> sub = query.subquery(Log.class);
				Expression<Log> exp = sub.from(Log.class);
				sub.select(sub.from(Log.class).get("user")).where(criteriaBuilder.equal(exp.get, questionnaireId));*/
				return null;
			}
		};
	}
}
