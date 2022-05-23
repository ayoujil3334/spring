package com.ayoujil.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "logiciels")
public class Logiciel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false, length = 50)
	private String name;
	
	@OneToMany(mappedBy="logiciel")
    private List <Ticket> tickets;

	public Logiciel(Long id, String name, List<Ticket> tickets) {
		super();
		this.id = id;
		this.name = name;
		this.tickets = tickets;
	}
	
	public Logiciel()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}
