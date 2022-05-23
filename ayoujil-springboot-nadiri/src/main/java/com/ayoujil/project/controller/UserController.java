package com.ayoujil.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.Role;
import com.ayoujil.project.model.User;
import com.ayoujil.project.service.RoleService;
import com.ayoujil.project.service.UserService;
import com.ayoujil.project.service.UserServiceImpl;

@Controller
@RequestMapping("/users")
public class UserController {
	// ------------------start responses messages for each action----
	private final String DELETE_SUCCEDED = "user has been deleted successfly!";
	private final String ADD_SUCCEDED = "user has been added successfly!";
	private final String UPDATE_SUCCEDED = "user has been updated successfly!";
	// ------------------end responses messages for each action -----
	// ------------------start messages names----
	private final String SUCCESS_MESSAGE = "successMessage";
	private final String WARNING_MESSAGE = "warningMessage";
	private final String DANGER_MESSAGE = "dangerMessage";
	// ------------------end messages names -----

	@Autowired
	public UserService userService;

	@Autowired
	public RoleService roleService;

	@GetMapping("/admins/page/{pageNumber}")
	public String findOneAdminsPage(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = userService.findAllAdmins(currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("usersRole", 1);

		return "admin_views/list_users";
	}

	@GetMapping("/developpers/page/{pageNumber}")
	public String findOneDeveloppersPage(Model model, @PathVariable("pageNumber") int currentPage) {

		Page<User> page = userService.findAllDeveloppers(currentPage);
		// page=(page.previousOrFirstPageable()).first();
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("usersRole", 2);

		return "admin_views/list_users";
	}

	@GetMapping("/clients/page/{pageNumber}")
	public String findOneClientsPage(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = userService.findAllClients(currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("usersRole", 3);

		return "admin_views/list_users";
	}

	@GetMapping("/admins")
	public String getAdmins(Model model) {
		return findOneAdminsPage(model, 1);
	}

	@GetMapping("/developpers")
	public String getDeveloppers(Model model) {
		return findOneDeveloppersPage(model, 1);
	}

	@GetMapping("/clients")
	public String getClients(Model model) {
		return findOneClientsPage(model, 1);
	}

	@GetMapping("/add")
	public String addUser(Model model) {
		User user = new User();
		List<Role> roles = roleService.findAll();

		model.addAttribute("user", user);
		model.addAttribute("roles", roles);

		return "admin_views/add_user";
	}

	@GetMapping("/edit/{id}")
	public String editUser(Model model, @PathVariable(required = true) long id) {
		User user = userService.findById(id);
		List<Role> roles = roleService.findAll();

		model.addAttribute("user", user);
		model.addAttribute("roles", roles);

		return "admin_views/edit_user";
	}

	@GetMapping("/show/{id}")
	public String getById(Model model, @PathVariable(required = true) long id) {
		model.addAttribute("user", userService.findById(id));

		return "admin_views/show_user";
	}

	@PostMapping("/create")
	public String add(RedirectAttributes redirAttrs, User user) {
		userService.save(user);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, ADD_SUCCEDED);
		return "redirect:/users/add";
	}

	@GetMapping("/delete/{id}")
	public String deleteById(RedirectAttributes redirAttrs, @PathVariable(required = true) int id) {
		userService.deleteById(id);
		redirAttrs.addFlashAttribute(DANGER_MESSAGE, DELETE_SUCCEDED);
		return "redirect:/users/developpers";
	}

	@PostMapping("/update")
	public String update(RedirectAttributes redirAttrs, User user) {
		userService.update(user);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, UPDATE_SUCCEDED);
		return "redirect:/users/edit/" + user.getId();
	}
}
