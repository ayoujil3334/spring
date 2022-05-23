package com.ayoujil.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.User;

public interface UserService {
	public void save(User user);

	public List<User> findAll();

	public User findById(Long long1);

	public void deleteById(int id);

	public void update(User user);

	public Page<User> findAllDeveloppers(int pageNumber);

	public Page<User> findAllClients(int pageNumber);

	public Page<User> findAllAdmins(int pageNumber);
	
	public User getUserByUsername(String username);

	public List<User> getAllDeveloppers();

	public List<User> getAllClients();

	public List<User> getAllAdmins();
	
	public User getUserByUserName(String userName);
}
