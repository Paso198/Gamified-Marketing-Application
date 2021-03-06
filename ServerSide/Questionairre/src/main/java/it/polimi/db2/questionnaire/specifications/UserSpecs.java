package it.polimi.db2.questionnaire.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.User;

public class UserSpecs {
	
	//"SELECT u FROM User u WHERE u IN (SELECT l.user FROM Log l WHERE l.questionnaire.id = ?1 AND l.action = 'SEND_QUESTIONNAIRE'"
	public static Specification<User> usersSentQuestionaire(Long questionnaireId){
		return new Specification<User>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Subquery<User> sub = query.subquery(User.class);
				Root<Log> log = sub.from(Log.class);
				sub.select(log.<User>get("user"))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(log.get("questionnaire").<Long>get("id"), questionnaireId),
						(criteriaBuilder.equal(log.<Action>get("action"),Action.SEND_QUESTIONNAIRE))));
				return criteriaBuilder.in(root).value(sub);
			}
		};
	}
	//"SELECT u FROM User u WHERE u IN (SELECT l.user FROM Log l WHERE l.questionnaire.id = ?1 AND l.action = 'CANCEL_QUESTIONNAIRE'"
		public static Specification<User> usersCancelledQuestionaire(Long questionnaireId){
			return new Specification<User>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					Subquery<User> sub = query.subquery(User.class);
					Root<Log> log = sub.from(Log.class);
					sub.select(log.<User>get("user"))
					.where(criteriaBuilder.and(
							criteriaBuilder.equal(log.get("questionnaire").<Long>get("id"), questionnaireId),
							(criteriaBuilder.equal(log.<Action>get("action"),Action.CANCEL_QUESTIONNAIRE))));
					return criteriaBuilder.in(root).value(sub);
				}
			};
		}
}
