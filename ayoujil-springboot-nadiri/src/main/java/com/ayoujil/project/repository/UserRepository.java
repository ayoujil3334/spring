package com.ayoujil.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ayoujil.project.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
	
	@Query(value = "select u.* from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'ADMIN'", nativeQuery = true)
	public Page <User> findAllAdmins(Pageable pageable);
	
	@Query(value = "select * from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'DEVELOPPER'", nativeQuery = true)
	public Page <User> findAllDeveloppers(Pageable pageable);
	
	@Query(value = "select u.* from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'CLIENT'", nativeQuery = true)
	public Page <User> findAllClients(Pageable pageable);
	
	@Query(value = "select u.* from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'ADMIN'", nativeQuery = true)
	public List <User> getAllAdmins();
	
	@Query(value = "select * from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'DEVELOPPER'", nativeQuery = true)
	public List <User> getAllDeveloppers();
	
	@Query(value = "select u.* from users u, roles r, users_roles ur where u.id = ur.user_id and r.id = ur.role_id and r.name = 'CLIENT'", nativeQuery = true)
	public List <User> getAllClients();
}
