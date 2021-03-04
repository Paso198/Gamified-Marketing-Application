package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	 @Basic(fetch=FetchType.EAGER)
	 @Lob		
	 @Column(name = "photo", columnDefinition="MEDIUMBLOB", nullable=false)
	 private byte[] photo;
	 
	 @OneToMany(mappedBy="product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Questionnaire> questionnaires;
	 
	 @OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Review> reviews;
}
