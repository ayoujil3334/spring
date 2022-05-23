package com.ayoujil.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.Notification;
import com.ayoujil.project.model.Role;
import com.ayoujil.project.model.Ticket;
import com.ayoujil.project.model.User;
import com.ayoujil.project.service.LogicielService;
import com.ayoujil.project.service.NotificationService;
import com.ayoujil.project.service.TicketService;
import com.ayoujil.project.service.UserService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	// ------------------start responses messages for each action----
	private final String DELETE_SUCCEDED = "ticket has been deleted successfly!";
	private final String ADD_SUCCEDED = "ticket has been added successfly!";
	private final String UPDATE_SUCCEDED = "ticket has been updated successfly!";
	// ------------------end responses messages for each action -----
	// ------------------start messages names----
	private final String SUCCESS_MESSAGE = "successMessage";
	private final String WARNING_MESSAGE = "warningMessage";
	private final String DANGER_MESSAGE = "dangerMessage";
	// ------------------end messages names -----

	@Autowired
	TicketService ticketService;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	UserService userService;

	@Autowired
	LogicielService logicielService;

	@GetMapping("/resolved")
	public String listResolvedTickets(Model model) {
		model.addAttribute("listTickets", ticketService.getTicketByStatus("RESOLVED"));
		return "admin_views/list_tickets";
	}

	@GetMapping("/processing")
	public String listProcessingTickets(Model model) {
		model.addAttribute("listTickets", ticketService.getTicketByStatus("PROCESSING"));
		return "admin_views/list_tickets";
	}

	@GetMapping("/pending")
	public String listPendingTickets(Model model) {
		model.addAttribute("listTickets", ticketService.getTicketByStatus("PENDING"));
		return "admin_views/list_tickets";
	}

	@GetMapping("/not-affected")
	public String listNotAffectedTickets(Model model) {
		model.addAttribute("listTickets", ticketService.getTicketByStatus("NOT_AFFECTED"));
		return "admin_views/list_tickets";
	}

	@GetMapping("/get/{id}")
	public String delete(@PathVariable(required = true) int id) {
		ticketService.findById(id);
		return "redirect:/tickets/add";
	}

	@GetMapping("/add")
	public String addTicket(Model model) {
		Ticket ticket = new Ticket();
		List<User> clients = userService.getAllClients();
		List<User> developpers = userService.getAllDeveloppers();
		List<Logiciel> logiciels = logicielService.findAll();
		model.addAttribute("ticket", ticket);
		model.addAttribute("clients", clients);
		model.addAttribute("developpers", developpers);
		model.addAttribute("logiciels", logiciels);

		return "admin_views/add_ticket";
	}

	@GetMapping("/show/{id}")
	public String getById(Model model, @PathVariable(required = true) int id) {
		model.addAttribute("ticket", ticketService.findById(id));
		if (ticketService.findById(id).getDevelopper() == null) {
			model.addAttribute("developpers", userService.getAllDeveloppers());
		}

		return "admin_views/show_ticket";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable(required = true) int id) {
		model.addAttribute("ticket", ticketService.findById(id));
		return "admin_views/edit_ticket";
	}

	@PostMapping("/create")
	public String add(RedirectAttributes redirAttrs, Ticket ticket) {
		ticket.setStatus("NOT_AFFECTED");
		ticketService.save(ticket);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, ADD_SUCCEDED);
		return "redirect:/tickets/pending";
	}

	@PostMapping("/affect/to/developper")
	public String setDevelopper(Ticket ticket) {
		ticketService.setDevelopper(ticket.getDevelopper().getId(), ticket.getId());
		Notification notification = new Notification();
		notification.setUser(userService.findById(ticketService.findTicketById(ticket.getId()).getClient().getId()));
		notification.setNotification("hi dear developper " + userService.findById(ticketService.findTicketById(ticket.getId()).getDevelopper().getId()).getUsername() + ", you have a new ticket!");
		notificationService.save(notification);
		return "redirect:/tickets/show/" + ticket.getId();
	}

	@GetMapping("/delete/{id}")
	public String deleteById(RedirectAttributes redirAttrs, @PathVariable(required = true) int id) {
		ticketService.deleteById(id);
		redirAttrs.addFlashAttribute(DANGER_MESSAGE, DELETE_SUCCEDED);
		return "redirect:/tickets/pending";
	}

	@PutMapping("/")
	public void update(@RequestBody Ticket ticket) {
		ticketService.update(ticket);
	}
}
