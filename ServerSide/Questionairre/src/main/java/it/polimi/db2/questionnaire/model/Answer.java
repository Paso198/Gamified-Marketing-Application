package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Answer {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Lob		//it could be heavy!
	@Column(nullable = false)
	private String text;

	@ManyToOne(optional=false) 
    @JoinColumn(name = "questionId", referencedColumnName = "id")
	private Question question;
}
