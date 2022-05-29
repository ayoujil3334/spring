package com.pfa.app.controllers;

import java.io.File;
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

import com.pfa.app.models.Dossier;
import com.pfa.app.models.Role;
import com.pfa.app.models.Specialite;
import com.pfa.app.models.TypeDossier;
import com.pfa.app.models.User;
import com.pfa.app.services.DossierService;
import com.pfa.app.services.RoleService;
import com.pfa.app.services.SpecialiteService;
import com.pfa.app.services.TypeDossierService;
import com.pfa.app.services.UserService;

@Controller
public class AuthController {
	@Autowired
	UserService userService;

	@Autowired
	SpecialiteService specialiteService;

	@Autowired
	RoleService roleService;

	@Autowired
	TypeDossierService typeDossierService;

	@Autowired
	DossierService dossierService;

	@GetMapping("/login")
	public String login(Model model) {
		return "auth_views/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		User user = new User();
		List<Specialite> specialites = specialiteService.findAll();
		model.addAttribute("user", user);
		model.addAttribute("specialites", specialites);
		return "auth_views/register";

	}

	@PostMapping("/register")
	public String create(RedirectAttributes redirAttrs, User user) {
		if (user.getEmail().equals("") || user.getUsername().equals("") || user.getPassword().equals("")
				|| user.getType().equals("") || user.getCin().equals("") || user.getNom().equals("")
				|| user.getPrenom().equals("") || user.getDbrp().equals("") || user.getDirecteurR().equals("")
				|| user.getDateRec().equals("")) {
			redirAttrs.addFlashAttribute("dangerMessage", "L'un des champs est vide !!!");
			return "redirect:/register";
		}
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleService.findByName("PROFESSEUR"));
		user.setRoles(roles);
		userService.save(user);

		// creation des dossiers (administratif, scientifique, pedagogique)
		List<TypeDossier> typesDossiers = typeDossierService.findAll();
		String userFolderName = user.getNom() + "_" + user.getPrenom() + "_" + user.getCin();
		File f0 = new File("global_folder/" + userFolderName);
		boolean bool0 = f0.mkdir();
		
		for (TypeDossier typeDossier : typesDossiers) {
			File f1 = new File("global_folder/" + userFolderName + "/" + typeDossier.getType());
			boolean bool1 = f1.mkdir();
		}
		
		for (TypeDossier typeDossier : typesDossiers) {
			Dossier dossier = new Dossier();
			dossier.setNom(userFolderName);
			dossier.setUser(user);
			dossier.setTypeDossier(typeDossier);
			dossierService.create(dossier);
		}

		redirAttrs.addFlashAttribute("successMessage", "Compte bien cree! Connectez vous :)!");
		return "redirect:/login";
	}

	@GetMapping("/")
	public String pageAfterLogin(Model model) {
		
		return "index";
	}
}
