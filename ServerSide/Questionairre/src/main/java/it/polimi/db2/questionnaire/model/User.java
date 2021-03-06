package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude= {"questionnairesCreated","responses", "logs", "reviews"})
@ToString(exclude= {"questionnairesCreated","responses", "logs", "reviews"})
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private Boolean blocked;
	
	@Column(nullable=false)
	private String roles;
	
	@OneToMany(mappedBy="creator", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Builder.Default
	private List<Questionnaire> questionnairesCreated = new ArrayList<Questionnaire>();
	
	@OneToMany(mappedBy="user")
	@Builder.Default
	private List<Response> responses = new ArrayList<Response>();
	
	@OneToMany(mappedBy="user")
	@Builder.Default
	private List<Log> logs = new ArrayList<Log>();
	
	@OneToMany(mappedBy="user")
	@Builder.Default
	private List<Review> reviews = new ArrayList<Review>();
	
	public Integer getPoints() {
		return responses.stream()
				.map(Response::getPoints)
				.reduce(0, Integer::sum);
	}
}
