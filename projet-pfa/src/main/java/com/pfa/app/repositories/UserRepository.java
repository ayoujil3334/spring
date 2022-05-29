package com.pfa.app.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfa.app.models.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
	
	@Query(value="select * from users u, users_roles ur, roles r where u.id = ur.user_id and ur.role_id = r.id and r.name = :role and u.type = :type", nativeQuery = true)
	public Page <User> getUserByRoleByType(Pageable pageable, @Param("role") String role, @Param("type") String type);
	
	@Query(value="select * from users u, users_roles ur, roles r where u.id = ur.user_id and ur.role_id = r.id and u.id = :professeurId and r.name = :role", nativeQuery = true)
	public User getUserByRoleByType(@Param("professeurId") Long professeurId, @Param("role") String role);
	
	@Modifying
	@Transactional
	@Query(value="update users u, users_roles ur, roles r set u.type = :type where u.id = ur.user_id and ur.role_id = r.id and u.id = :professeurId and r.name = :role", nativeQuery = true)
	public void changeTypeUser(@Param("professeurId") Long professeurId, @Param("role") String role, @Param("type") String type);
}
