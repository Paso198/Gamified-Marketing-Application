package it.polimi.db2.questionnaire.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude= {"questionnaires","reviews"})
@ToString(exclude= {"questionnaires","reviews"})
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
	 @Builder.Default
	 private List<Questionnaire> questionnaires= new ArrayList<Questionnaire>();
	 
	 @OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	 @Builder.Default
	 private List<Review> reviews= new ArrayList<Review>();
}
