package com.ayoujil.project.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoujil.project.model.Role;
import com.ayoujil.project.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleApi {
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/")
    public List <Role> findAll() {
        return roleRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Role getById(@PathVariable(required = true) int id) {
        return roleRepository.findById((long) id).get();
    }
	
	@PostMapping("/")
    public void add(Role role) {
        roleRepository.save(role);
    }
	
	@DeleteMapping("/{id}")
    public void deleteById(@PathVariable(required = true) int id) {
		roleRepository.deleteById((long) id);
    }
	
	@PutMapping("/")
    public void update(@RequestBody Role role) {
		Role oldRole = roleRepository.findById(role.getId()).get();
		if(oldRole != null)
		{
			
			roleRepository.save(oldRole);
		}
    }
}
