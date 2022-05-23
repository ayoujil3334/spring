package com.ayoujil.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "roles")
public class Role {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable=false, length = 50)
    private String name;
    
    @JsonBackReference
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List <User> users;

	public Role(Long id, String name, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}
    
	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Role(String name) {
		super();
		this.name = name;
	}
	
	public Role()
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}