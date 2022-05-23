package com.ayoujil.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ayoujil.project.model.Role;
import com.ayoujil.project.model.User;
import com.ayoujil.project.service.RoleService;
import com.ayoujil.project.service.UserService;

@Controller
public class AuthController {
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/login")
    public String login(Model model) {
		return "auth_views/login";
    }
	
	@GetMapping("/register")
    public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "auth_views/register";
    }
	
	@PostMapping("/register")
    public String create(RedirectAttributes redirAttrs, User user) {
		if(user.getEmail().equals("") || user.getUsername().equals("") || user.getPassword().equals(""))
		{
			redirAttrs.addFlashAttribute("dangerMessage", "required fields are empty!");
			return "redirect:/register";
		}
		List <Role> roles = new ArrayList <Role> ();
		roles.add(roleService.findByName("CLIENT"));
		user.setRoles(roles);
		userService.save(user);
		redirAttrs.addFlashAttribute("successMessage", "your account has been created successfly! <use your credentials to login.>");
		return "redirect:/login";
    }
	
	@GetMapping("/home")
    public String pageAfterLogin(Model model) {
		return "index";
    }
}
