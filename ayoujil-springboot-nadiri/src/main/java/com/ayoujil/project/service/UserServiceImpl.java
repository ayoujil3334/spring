package com.ayoujil.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ayoujil.project.config.MyUserDetails;
import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.User;
import com.ayoujil.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public List <User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById((long) id);
	}

	@Override
	public void update(User user) {
		User oldUser = userRepository.findById(user.getId()).get();
		if(oldUser != null)
		{
			oldUser.setEmail(user.getEmail());
			oldUser.setUsername(user.getUsername());
			oldUser.setRoles(user.getRoles());
			userRepository.save(oldUser);
		}
	}

	@Override
	public Page<User> findAllDeveloppers(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
	    return userRepository.findAllDeveloppers(pageable);
	}

	@Override
	public Page<User> findAllClients(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
	    return userRepository.findAllClients(pageable);
	}

	@Override
	public Page<User> findAllAdmins(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 1);
	    return userRepository.findAllAdmins(pageable);
	}

	@Override
	public List<User> getAllDeveloppers() {
		return userRepository.getAllDeveloppers();
	}

	@Override
	public List<User> getAllClients() {
		return userRepository.getAllClients();
	}

	@Override
	public List<User> getAllAdmins() {
		return userRepository.getAllAdmins();
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUsername(userName);
	}

	@Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
    }

	@Override
	public User getUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}
}
