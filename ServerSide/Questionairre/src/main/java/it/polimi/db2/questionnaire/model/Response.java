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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    @JoinColumn(name = "questionnaireId", referencedColumnName = "id")
	private Questionnaire questionnaire;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	@JoinColumn(name = "submitter", referencedColumnName = "id")
	private User user;
	
	@OneToMany(mappedBy="response", cascade = CascadeType.ALL, orphanRemoval = true)
	private List <Answer> answers;

}
