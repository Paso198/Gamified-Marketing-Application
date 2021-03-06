package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import it.polimi.db2.questionnaire.enumerations.ExpertiseLevel;
import it.polimi.db2.questionnaire.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude= "answers")
@ToString(exclude="answers")
@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"questionnaire_id", "user_id"})
	)
public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	private ExpertiseLevel expertiseLevel;
	
	private Integer points;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER) 	
	private Questionnaire questionnaire;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	private User user;
	
	@OneToMany(mappedBy="response", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List <Answer> answers = new ArrayList<Answer>();
	
	@PostPersist
	public void calculatePoints() {
		Integer points = 0;
		if(age!=null) {
			points+=2;
		}
		if(gender!=null) {
			points+=2;
		}
		if(expertiseLevel!=null) {
			points+=2;
		}
		points+=answers.size();
		this.points=points;
	}
	
	public void addAnswer(Answer answer) {
		this.answers.add(answer);
		answer.setResponse(this);
	}
}
