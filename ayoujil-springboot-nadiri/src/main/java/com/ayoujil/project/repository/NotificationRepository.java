package com.ayoujil.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ayoujil.project.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long> {
	@Query(value = "select * from notifications n, users u where u.id = n.user_id and n.is_read = :isRead and n.user_id = :userId", nativeQuery = true)
	public List<Notification> findAllNotificationsByUserAndByStatus(@Param("userId") Long userId, @Param("isRead") boolean isRead);
	
	@Modifying
	@Transactional
	@Query("UPDATE Notification n SET n.isRead = true WHERE n.id = :id")
    public void makeNotificationAsRead(@Param("id") Long id);
}
