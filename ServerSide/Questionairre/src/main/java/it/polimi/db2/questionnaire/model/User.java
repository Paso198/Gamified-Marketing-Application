package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

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
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	private List<Questionnaire> questionnairesCreated;
	
	@OneToMany(mappedBy="user")
	private List<Response> responses;
	
	@OneToMany(mappedBy="user")
	private List<Log> logs;
	
	@OneToMany(mappedBy="user")
	private List<Review> reviews;
}
