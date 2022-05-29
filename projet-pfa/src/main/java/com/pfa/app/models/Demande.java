package com.pfa.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "demandes")
public class Demande {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="motivation", nullable=false, length=500)
	private String motivation;
	
	@Column(name="dateCreation", nullable=true, length=50)
	private String dateCreation;
	
	@ManyToOne
	private User professeur;

	public Demande(Long id, String status, String dateCreation, String motivation, User professeur) {
		super();
		this.id = id;
		this.status = status;
		this.dateCreation = dateCreation;
		this.motivation = motivation;
		this.professeur = professeur;
	}
	
	public Demande()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status = status;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public User getProfesseur() {
		return professeur;
	}

	public void setProfesseur(User professeur) {
		this.professeur = professeur;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}
}
