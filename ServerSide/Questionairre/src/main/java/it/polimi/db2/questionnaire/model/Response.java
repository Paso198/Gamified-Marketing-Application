package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import it.polimi.db2.questionnaire.enumerations.ExpertiseLevel;
import it.polimi.db2.questionnaire.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
	private List <Answer> answers;
}
