package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
@EqualsAndHashCode(exclude= {"questionnaires","answers"})
@ToString(exclude= {"questionnaires","answers"})
public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String text;
	
	@ManyToMany(mappedBy="questions")
	@Builder.Default
	private List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
	
	@OneToMany(mappedBy="question")
	@Builder.Default
	private List<Answer> answers = new ArrayList<Answer>();
}
