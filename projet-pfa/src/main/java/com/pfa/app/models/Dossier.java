package com.pfa.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dossiers")
public class Dossier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nom", nullable = false)
	private String nom;

	@ManyToOne
	private TypeDossier typeDossier;

	@ManyToOne
	private User user;

	public Dossier(Long id, String nom, TypeDossier typeDossier) {
		super();
		this.id = id;
		this.nom = nom;
		this.typeDossier = typeDossier;
	}
	
	public Dossier(String nom, TypeDossier typeDossier, User user) {
		super();
		this.nom = nom;
		this.typeDossier = typeDossier;
		this.user = user;
	}

	public Dossier() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public TypeDossier getTypeDossier() {
		return typeDossier;
	}

	public void setTypeDossier(TypeDossier typeDossier) {
		this.typeDossier = typeDossier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
