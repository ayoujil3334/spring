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
import com.ayoujil.project.repository.LogicielRepository;
import com.ayoujil.project.service.LogicielService;

@Controller
@RequestMapping("/logiciels")
public class LogicielController {
	// ------------------start responses messages for each action----
	private final String DELETE_SUCCEDED = "logiciel has been deleted successfly!";
	private final String ADD_SUCCEDED = "logiciel has been added successfly!";
	private final String UPDATE_SUCCEDED = "logiciel has been updated successfly!";
	// ------------------end responses messages for each action -----
	// ------------------start messages names----
	private final String SUCCESS_MESSAGE = "successMessage";
	private final String WARNING_MESSAGE = "warningMessage";
	private final String DANGER_MESSAGE = "dangerMessage";
	// ------------------end messages names -----

	@Autowired
	private LogicielService logicielService;

	@GetMapping("/page/{pageNumber}")
	public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Logiciel> page = logicielService.findPage(currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Logiciel> logiciels = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listLogiciels", logiciels);

		return "admin_views/list_logiciels";
	}

	@GetMapping
	public String getAllPages(Model model) {
		return getOnePage(model, 1);
	}

	@GetMapping("/add")
	public String addLogiciel(Model model) {
		Logiciel logiciel = new Logiciel();
		model.addAttribute("logiciel", logiciel);
		return "admin_views/add_logiciel";
	}

	@GetMapping("/{id}")
	public Logiciel getById(@PathVariable(required = true) int id) {
		return logicielService.findById(id);
	}

	@PostMapping("/create")
	public String add(RedirectAttributes redirAttrs, Logiciel _logiciel) {
		logicielService.save(_logiciel);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, ADD_SUCCEDED);
		return "redirect:/logiciels/add";
	}

	@GetMapping("/delete/{id}")
	public String deleteById(RedirectAttributes redirAttrs, Model model, @PathVariable(required = true) int id) {
		logicielService.deleteById(id);
		redirAttrs.addFlashAttribute(DANGER_MESSAGE, DELETE_SUCCEDED);
		return "redirect:/logiciels/";
	}

	@GetMapping("/edit/{id}")
	public String updateLogiciel(Model model, @PathVariable(required = true) int id) {
		Logiciel logiciel = logicielService.findById(id);
		model.addAttribute("logiciel", logiciel);
		return "admin_views/edit_logiciel";
	}

	@PostMapping("/update")
	public String update(RedirectAttributes redirAttrs, Logiciel _logiciel) {
		logicielService.update(_logiciel);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, UPDATE_SUCCEDED);
		return "redirect:/logiciels/edit/" + _logiciel.getId();
	}
}
