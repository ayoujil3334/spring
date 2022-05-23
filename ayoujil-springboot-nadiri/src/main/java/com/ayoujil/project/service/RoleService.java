package com.ayoujil.project.service;

import java.util.List;

import com.ayoujil.project.model.Role;

public interface RoleService {
	public void save(Role role);

	public List<Role> findAll();

	public Role findById(int id);

	public void deleteById(int id);

	public void update(Role role);
	
	public Role findByName(String name);
}
