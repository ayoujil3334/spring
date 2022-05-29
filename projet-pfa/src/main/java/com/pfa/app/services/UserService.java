package com.pfa.app.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pfa.app.models.User;

public interface UserService {
	public void save(User user);

	public List<User> findAll();

	public User findById(Long long1);

	public void deleteById(int id);

	public void update(User user);
	
	public User getUserByUserName(String userName);

	public Page<User> getUserByRoleByType(int currentPage, String role, String type);
	
	public User getUserByRoleByType(Long professeurId, String role);
	
	public void changeTypeUser(Long professeurId, String role, String type);
	
	public boolean upload(MultipartFile file);
	
	public boolean deleteIfExists(String imageName) throws IOException;
}
