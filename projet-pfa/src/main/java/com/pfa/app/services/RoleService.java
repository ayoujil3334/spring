package com.pfa.app.services;

import java.util.List;

import com.pfa.app.models.Role;

public interface RoleService {
	public Role findById(Long id);

	public List<Role> findAll();

	public void create(Role role);

	public void update(Role role);

	public void deleteById(Long id);

	public Role findByName(String name);
}
