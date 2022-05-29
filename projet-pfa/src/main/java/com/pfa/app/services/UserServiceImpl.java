package com.pfa.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pfa.app.config.MyUserDetails;
import com.pfa.app.models.User;
import com.pfa.app.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;

	private Path root = null;

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
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
		if (oldUser != null) {
			oldUser.setCin(user.getCin());
			oldUser.setDateRec(user.getDateRec());
			oldUser.setDirecteurR(user.getDirecteurR());
			oldUser.setDbrp(user.getDbrp());
			oldUser.setSpecialite(user.getSpecialite());
			oldUser.setNom(user.getNom());
			oldUser.setPrenom(user.getPrenom());
			oldUser.setImage(user.getImage());
			oldUser.setStructureR(user.getStructureR());
			oldUser.setTel(user.getTel());
			// oldUser.setEmail(user.getEmail());
			// oldUser.setUsername(user.getUsername());
			// oldUser.setRoles(user.getRoles());
			userRepository.save(oldUser);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		return new MyUserDetails(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUsername(userName);
	}

	@Override
	public Page<User> getUserByRoleByType(int pageNumber, String role, String type) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return userRepository.getUserByRoleByType(pageable, role, type);
	}

	@Override
	public User getUserByRoleByType(Long professeurId, String role) {
		return userRepository.getUserByRoleByType(professeurId, role);
	}

	@Override
	public void changeTypeUser(Long professeurId, String role, String type) {
		userRepository.changeTypeUser(professeurId, role, type);
	}

	@Override
	public boolean upload(MultipartFile file) {
		try {
			root = Paths.get("src/main/resources/static/images/users");
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			// Files.deleteIfExists(root);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteIfExists(String imageName) throws IOException {
		root = Paths.get("src/main/resources/static/images/users/" + imageName);
		return Files.deleteIfExists(root);
	}
}
