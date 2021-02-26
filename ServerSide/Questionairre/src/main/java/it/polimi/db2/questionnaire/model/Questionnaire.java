package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.time.LocalDate;
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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
	private List<Question> questions;
	
	@OneToMany(mappedBy="questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Response> responses;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY) 
	@JoinColumn(name = "creator", referencedColumnName = "id")
	private User user;
	
	@OneToMany(mappedBy="questionnaire")
	private List<Log> logs;
}
