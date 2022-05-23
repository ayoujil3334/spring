package com.ayoujil.project.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = false, length = 500)
	private String description;

	@Column(name = "environment", nullable = false, length = 500)
	private String environment;

	@Column(name = "priority", nullable = false, length = 20)
	private String priority;

	@Column(name = "status", nullable = true, length = 20)
	@ColumnDefault("'NOT_AFFECTED'")
	private String status;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "logiciel_id", nullable = false)
	private Logiciel logiciel;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = true)
	private User client;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "developper_id", nullable = true)
	private User developper;

	public Ticket(Long id, String description, String environment, String priority, String status, Logiciel logiciel,
			User client, User developper) {
		super();
		this.id = id;
		this.description = description;
		this.environment = environment;
		this.priority = priority;
		this.status = status;
		this.logiciel = logiciel;
		this.client = client;
		this.developper = developper;
	}

	public Ticket() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Logiciel getLogiciel() {
		return logiciel;
	}

	public void setLogiciel(Logiciel logiciel) {
		this.logiciel = logiciel;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public User getDevelopper() {
		return developper;
	}

	public void setDevelopper(User developper) {
		this.developper = developper;
	}
}
