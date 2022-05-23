package com.ayoujil.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayoujil.project.model.Notification;
import com.ayoujil.project.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public void save(Notification notification) {
		notificationRepository.save(notification);
	}

	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}

	@Override
	public Notification findById(int id) {
		return notificationRepository.findById((long) id).get();
	}

	@Override
	public void deleteById(int id) {
		notificationRepository.deleteById((long) id);
	}

	@Override
	public void update(Notification notification) {
		//TODO
	}

	@Override
	public List<Notification> findAllNotificationsByUserAndByStatus(Long userId, boolean isRead) {
		return notificationRepository.findAllNotificationsByUserAndByStatus(userId, isRead);
	}

	@Override
	public void makeNotificationAsRead(Long id) {
		notificationRepository.makeNotificationAsRead(id);
	}

}
