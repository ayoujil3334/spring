package com.pfa.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pfa.app.models.User;
import com.pfa.app.services.UserService;

@Controller
@RequestMapping("/users")
public class UserConrtoller {
	@Autowired
	UserService userService;
	
	/*@GetMapping("/professeurs/pesa/page/{pageNumber}")
    public String getOnePageProfessers(Model model, @PathVariable("pageNumber") int currentPage) {	
		Page<User> page = userService.getProfesseursPesa(currentPage);
	    int totalPages = page.getTotalPages();
	    	
	    long totalItems = page.getTotalElements();
	    List<User> users = page.getContent();

	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalItems", totalItems);
	    model.addAttribute("listUsers", users);
	    
        return "admin_views/list_users";
    }
	
	@GetMapping("/professeurs/pesa")
	public String getAdmins(Model model){
	    return getOnePageProfessers(model, 1);
	}
	
	@GetMapping("/professeurs/pes/page/{pageNumber}")
    public String getOnePageProfessersPes(Model model, @PathVariable("pageNumber") int currentPage) {	
		Page<User> page = userService.getProfesseursPes(currentPage);
	    int totalPages = page.getTotalPages();
	    	
	    long totalItems = page.getTotalElements();
	    List<User> users = page.getContent();

	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalItems", totalItems);
	    model.addAttribute("listUsers", users);
	    
        return "admin_views/list_users";
    }
	
	@GetMapping("/professeurs/pes")
	public String getProfesseursPes(Model model){
	    return getOnePageProfessersPes(model, 1);
	}
	
	@GetMapping("/professeurs/ph/page/{pageNumber}")
    public String getOnePageProfessersPh(Model model, @PathVariable("pageNumber") int currentPage) {	
		Page<User> page = userService.getProfesseursPh(currentPage);
	    int totalPages = page.getTotalPages();
	    	
	    long totalItems = page.getTotalElements();
	    List<User> users = page.getContent();

	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("totalItems", totalItems);
	    model.addAttribute("listUsers", users);
	    
        return "admin_views/list_users";
    }
	
	@GetMapping("/professeurs/ph")
	public String getProfesseursPh(Model model){
	    return getOnePageProfessersPh(model, 1);
	}*/
}
