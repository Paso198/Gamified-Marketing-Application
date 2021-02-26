package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
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
public class Log implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String action;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER) 
	@JoinColumn(name = "questionnaireId", referencedColumnName = "id")
	private Questionnaire questionnaire;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER) 
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;

}
