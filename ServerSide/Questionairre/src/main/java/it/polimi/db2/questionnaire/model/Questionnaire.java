package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(exclude= {"questions","responses", "logs"})
@ToString(exclude= {"questions","responses", "logs"})
public class Questionnaire implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private String title;
	
	@ManyToOne(optional=false) 
	@JoinColumn(name = "productId", referencedColumnName = "id")
	private Product product;
	
	@ManyToMany
	@JoinTable(
			name="contains",
			joinColumns = @JoinColumn(name = "questionnaireId"),
			inverseJoinColumns = @JoinColumn(name = "questionId"))
	@Builder.Default
	private List<Question> questions=new ArrayList<Question>();
	
	@OneToMany(mappedBy="questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Response> responses=new ArrayList<Response>();
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	private User creator;
	
	@OneToMany(mappedBy="questionnaire")
	@Builder.Default
	private List<Log> logs=new ArrayList<Log>();
	
	public void addQuestion(Question question) {
		this.questions.add(question);
		question.getQuestionnaires().add(this);
	}
}
