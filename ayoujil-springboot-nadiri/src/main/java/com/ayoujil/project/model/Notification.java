package com.ayoujil.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="notification", nullable=false, length = 500)
    private String notification;
    
    @Column(name="isRead")
    private boolean isRead;
    
    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Notification(String notification, boolean isRead, User user) {
		super();
		this.notification = notification;
		this.isRead = isRead;
		this.user = user;
	}
    
    public Notification()
    {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
