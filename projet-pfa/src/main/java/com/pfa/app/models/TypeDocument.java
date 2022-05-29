package com.pfa.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "types_documents")
public class TypeDocument {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="type", nullable=false)
	private String type;

	@ManyToOne
	private TypeDossier typeDossier;
	
	public TypeDocument(Long id, String type, TypeDossier typeDossier) {
		super();
		this.id = id;
		this.type = type;
		this.typeDossier = typeDossier;
	}
	
	public TypeDocument()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TypeDossier getTypeDossier() {
		return typeDossier;
	}

	public void setTypeDossier(TypeDossier typeDossier) {
		this.typeDossier = typeDossier;
	}
}
