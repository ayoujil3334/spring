package com.ayoujil.project.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ayoujil.project.model.Notification;

public interface NotificationService {
	public void save(Notification notification);

	public List<Notification> findAll();

	public Notification findById(int id);

	public void deleteById(int id);

	public void update(Notification notification);
	
	public List <Notification> findAllNotificationsByUserAndByStatus(Long userId, boolean isRead);
	
	public void makeNotificationAsRead(Long id);
}
