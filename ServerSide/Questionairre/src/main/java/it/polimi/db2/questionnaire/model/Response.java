package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	private String gender;
	
	private String expertiseLevel;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 	
    @JoinColumn(name = "questionnaireId", referencedColumnName = "id")
	private Questionnaire questionnaire;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	@JoinColumn(name = "submitter", referencedColumnName = "id")
	private User user;
	

	
}
