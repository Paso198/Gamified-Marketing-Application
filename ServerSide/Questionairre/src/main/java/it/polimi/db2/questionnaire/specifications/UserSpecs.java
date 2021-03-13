package it.polimi.db2.questionnaire.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;

public class UserSpecs {
	
	//"SELECT r.user FROM Response r WHERE r.questionnaire.id = ?1 ORDER BY r.points DESC"
	public static Specification<User> usersSentQuestionaire(Long questionnaireId){
		return new Specification<User>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				/*Root<Response> subroot = query.from(Response.class);
				query.subquery(User.class).select(subroot.<User>get("user"));
				query.orderBy(criteriaBuilder.desc(subroot.get("points")));*/
				Join<User,Response> responses= root.join("responses");
				query.orderBy(criteriaBuilder.desc(responses.get("points")));
				return criteriaBuilder.equal(responses.get("questionnaire").<Long>get("id"), questionnaireId);
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
