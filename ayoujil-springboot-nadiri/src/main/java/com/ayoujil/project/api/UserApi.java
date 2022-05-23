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

import com.ayoujil.project.model.User;
import com.ayoujil.project.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserApi {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
    public List <User> findAll() {
        return userRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public User getById(@PathVariable(required = true) int id) {
        return userRepository.findById((long) id).get();
    }
	
	@PostMapping("/")
    public void add(User user) {
        userRepository.save(user);
    }
	
	@DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable(required = true) int id) {
        userRepository.deleteById((long) id);
    }
	
	@PutMapping("/")
    public void update(@RequestBody User user) {
		User oldUser = userRepository.findById(user.getId()).get();
		if(oldUser != null)
		{
			oldUser.setEmail(user.getEmail());
			oldUser.setUsername(user.getUsername());
			userRepository.save(oldUser);
		}
    }
}
