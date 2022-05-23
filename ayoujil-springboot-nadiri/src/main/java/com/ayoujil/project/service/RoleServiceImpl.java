package com.ayoujil.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayoujil.project.model.Role;
import com.ayoujil.project.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(int id) {
		return roleRepository.findById((long) id).get();
	}

	@Override
	public void deleteById(int id) {
		roleRepository.deleteById((long) id);
	}

	@Override
	public void update(Role role) {
		Role oldRole = roleRepository.findById(role.getId()).get();
		if(oldRole != null)
		{
			oldRole.setName(role.getName());
			roleRepository.save(oldRole);
		}
	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
