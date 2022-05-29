package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfa.app.models.Role;
import com.pfa.app.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public void create(Role role) {
		roleRepository.save(role);
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
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}
	
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
