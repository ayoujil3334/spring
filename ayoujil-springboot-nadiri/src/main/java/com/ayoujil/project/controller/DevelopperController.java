package com.ayoujil.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ayoujil.project.model.Notification;
import com.ayoujil.project.service.LogicielService;
import com.ayoujil.project.service.NotificationService;
import com.ayoujil.project.service.TicketService;
import com.ayoujil.project.service.UserService;

@Controller
@RequestMapping("/developper/profile")
public class DevelopperController {
	// ------------------start responses messages for each action----
	private final String TICKET_TO_PROCESSING_SUCCEDED = "ticket has been passed successfly to processing status!";
	private final String TICKET_TO_RESOLVED_SUCCEDED = "ticket has been passed successfly to resolved status!";
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
				ticketService.getTicketByDevelopper(userService.getUserByUserName(auth.getName()).getId(), "RESOLVED"));
		model.addAttribute("isAction", false);
		return "developper_views/list_tickets";
	}

	@GetMapping("/tickets/processing")
	public String listProcessingTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tickets", ticketService
				.getTicketByDevelopper(userService.getUserByUserName(auth.getName()).getId(), "PROCESSING"));
		model.addAttribute("isAction", true);
		return "developper_views/list_tickets";
	}

	@GetMapping("/tickets/pending")
	public String listPendingTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tickets",
				ticketService.getTicketByDevelopper(userService.getUserByUserName(auth.getName()).getId(), "PENDING"));
		model.addAttribute("isAction", true);
		return "developper_views/list_tickets";
	}

	@GetMapping("/tickets/pass/to/processing/{id}")
	public String passTicketToProcessing(RedirectAttributes redirAttrs, Model model, @PathVariable(required = true) Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ticketService.changeTicketStatus(id, "PROCESSING");
		//send notification to client
		Notification notification = new Notification();
		notification.setUser(userService.findById(ticketService.findTicketById(id).getClient().getId()));
		notification.setNotification("hi dear client " + userService.findById(ticketService.findTicketById(id).getClient().getId()).getUsername() + ", your ticket number <" + ticketService.findTicketById(id).getId() + "> is passed to processing status!");
		notificationService.save(notification);
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, TICKET_TO_PROCESSING_SUCCEDED);
		return "redirect:/developper/profile/tickets/pending";
	}

	@GetMapping("/tickets/pass/to/resolved/{id}")
	public String passTicketToResolved(RedirectAttributes redirAttrs, Model model, @PathVariable(required = true) Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Notification notification = new Notification();
		notification.setUser(userService.findById(ticketService.findTicketById(id).getClient().getId()));
		notification.setNotification("hi dear client " + userService.findById(ticketService.findTicketById(id).getClient().getId()).getUsername() + ", your ticket number <" + ticketService.findTicketById(id).getId() + "> is passed to resolved status!");
		notificationService.save(notification);
		ticketService.changeTicketStatus(id, "RESOLVED");
		redirAttrs.addFlashAttribute(SUCCESS_MESSAGE, TICKET_TO_RESOLVED_SUCCEDED);
		return "redirect:/developper/profile/tickets/processing";
	}

	@GetMapping("/tickets/show/{id}")
	public String get(Model model, @PathVariable(required = true) int id) {
		model.addAttribute("ticket", ticketService.findById(id));
		return "developper_views/show_ticket";
	}
}
