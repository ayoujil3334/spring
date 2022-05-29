package com.pfa.app.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pfa.app.models.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nom", nullable=true, length=50)
	private String nom;
	
	@Column(name="prenom", nullable=true, length=50)
	private String prenom;
	
	@Column(name="username", nullable=false, length=255)
	private String username;
	
	@Column(name="email", nullable=false, length=255)
	private String email;
	
	@Column(name="password", nullable=false, length=512)
	private String password;
	
	@Column(name="type", nullable=true, length=50)
	private String type;
	
	@Column(name="image", nullable=true, length=500, columnDefinition = "varchar(500) default 'default.png'")
	private String image;

	@Column(name="cin", nullable=true, length=50)
	private String cin;
	
	@Column(name="dbrp", nullable=true, length=50)
	private String dbrp;
	
	@Column(name="dateRec", nullable=true, length=50)
	private String dateRec;
	
	@ManyToOne
	private Specialite specialite;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private List <Role> roles;
	
	@Column(name="tel", nullable=true, length=50)
	private String tel;
	
	@Column(name="isEnsa", nullable=false)
	private boolean isEnsa;
	
	@Column(name="structureR", nullable=true, length=50)
	private String structureR;
	
	@Column(name="directeurR", nullable=true, length=50)
	private String directeurR;
	
	public User() {
		
	}

	public User(Long id, String nom, String prenom, String username, String email, String type, String password, List <Role> roles, String cin, String dbrp,
			String dateRec, String tel, boolean isEnsa, String structureR, String directeurR) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.type = type;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.cin = cin;
		this.dbrp = dbrp;
		this.dateRec = dateRec;
		this.tel = tel;
		this.isEnsa = isEnsa;
		this.structureR = structureR;
		this.directeurR = directeurR;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
	
	public String getType() {
		return type;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List <Role> getRole() {
		return roles;
	}

	public void setRole(List <Role> roles) {
		this.roles = roles;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getDbrp() {
		return dbrp;
	}

	public void setDbrp(String dbrp) {
		this.dbrp = dbrp;
	}

	public String getDateRec() {
		return dateRec;
	}

	public void setDateRec(String dateRec) {
		this.dateRec = dateRec;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean isEnsa() {
		return isEnsa;
	}

	public void setEnsa(boolean isEnsa) {
		this.isEnsa = isEnsa;
	}

	public String getStructureR() {
		return structureR;
	}

	public void setStructureR(String strutureR) {
		this.structureR = strutureR;
	}

	public String getDirecteurR() {
		return directeurR;
	}

	public void setDirecteurR(String directeurR) {
		this.directeurR = directeurR;
	}
	
	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
