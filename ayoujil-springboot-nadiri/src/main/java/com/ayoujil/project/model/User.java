package com.ayoujil.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", nullable=false, length = 50)
	private String username;
	
	@Column(name="password", nullable=false, length = 50)
	private String password;
	
	@Column(name="email", nullable=false, length = 255)
	private String email;
	
	/*@OneToMany
    private List <Ticket> tickets;*/
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private List <Role> roles;

	public User(Long id, String username, String password, String email, /*List<Ticket> tickets, */List<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		//this.tickets = tickets;
		this.roles = roles;
	}
	
	public User()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}*/

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
