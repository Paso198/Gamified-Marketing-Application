package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class Answer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Lob		//it could be heavy!
	@Column(nullable = false)
	private String text;

	@ManyToOne(optional=false) 			
	private Question question;
	
	@ManyToOne(optional=false)
	private Response response;
}
