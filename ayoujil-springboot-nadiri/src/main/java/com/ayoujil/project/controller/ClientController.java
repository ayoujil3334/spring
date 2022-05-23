package com.ayoujil.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.Ticket;
import com.ayoujil.project.model.User;
import com.ayoujil.project.service.LogicielService;
import com.ayoujil.project.service.NotificationService;
import com.ayoujil.project.service.TicketService;
import com.ayoujil.project.service.UserService;

@Controller
@RequestMapping("/client/profile")
public class ClientController {
	// ------------------start responses messages for each action----
	private final String TICKET_DELETE_SUCCEDED = "ticket has been deleted successfly!";
	private final String TICKET_ADD_SUCCEDED = "ticket has been added successfly!";
	private final String TICKET_UPDATE_SUCCEDED = "ticket has been updated successfly!";
	// ------------------end responses messages for each action -----
	// ------------------start messages names----
	private final String SUCCESS_MESSAGE = "successMessage";
	private final String WARNING_MESSAGE = "warningMessage";
	private final String DANGER_MESSAGE = "dangerMessage";
	// ------------------end messages names -----

	@Autowired
	TicketService ticketService;

	@Autowired
	UserService userService;
	
	@Autowired
	NotificationService notificationService;

	@Autowired
	LogicielService logicielService;

	@GetMapping("/tickets/resolved")
	public String listResolvedTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tickets",
				ticketService.getTicketByClient(userService.getUserByUserName(auth.getName()).getId(), "RESOLVED"));
		System.out.println("user_id: " + userService.getUserByUserName(auth.getName()).getId());
		model.addAttribute("notifications", notificationService.findAllNotificationsByUserAndByStatus(userService.getUserByUserName(auth.getName()).getId(), false));
		return "client_views/list_tickets";
	}

	@GetMapping("/tickets/processing")
	public String listProcessingTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tickets",
				ticketService.getTicketByClient(userService.getUserByUserName(auth.getName()).getId(), "PROCESSING"));
		return "client_views/list_tickets";
	}

	@GetMapping("/tickets/pending")
	public String listPendingTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tickets",
				ticketService.getTicketByClient(userService.getUserByUserName(auth.getName()).getId(), "PENDING"));
		return "client_views/list_tickets";
	}

	@GetMapping("/tickets/add")
	public String addTicket(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("logiciels", logicielService.findAll());

		return "client_views/add_ticket";
	}

	@PostMapping("/tickets/create")
	public String create(RedirectAttributes redirAttrs, Ticket ticket) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ticket.setStatus("NOT_AFFECTED");
		ticket.setClient(userService.getUserByUserName(auth.getName()));
		ticket.setDevelopper(null);
		ticketService.save(ticket);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, TICKET_DELETE_SUCCEDED);
		return "redirect:/client/profile/tickets/pending";
	}

	@GetMapping("/tickets/show/{id}")
	public String get(Model model, @PathVariable(required = true) int id) {
		model.addAttribute("ticket", ticketService.findById(id));

		return "client_views/show_ticket";
	}
}
