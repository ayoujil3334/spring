package com.ayoujil.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ayoujil.project.service.NotificationService;
import com.ayoujil.project.service.TicketService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	TicketService ticketService;
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/doashboard")
	public String calculate(Model model) {
		model.addAttribute("pendingPercent",  (((ticketService.getNumberOfTicketsByStatus("PENDING")) * 100) / ticketService.getNumberOfAllTickets()));
		model.addAttribute("processingPercent",  (((ticketService.getNumberOfTicketsByStatus("PROCESSING")) * 100) / ticketService.getNumberOfAllTickets()));
		model.addAttribute("resolvedPercent",  (((ticketService.getNumberOfTicketsByStatus("RESOLVED")) * 100) / ticketService.getNumberOfAllTickets()));
		model.addAttribute("notAffectedPercent",  (((ticketService.getNumberOfTicketsByStatus("NOT_AFFECTED")) * 100) / ticketService.getNumberOfAllTickets()));
	    return "admin_views/doashboard";
	}
	
	@GetMapping("/notifications/make/it/read/{id}")
	public String readNot(Model model, @PathVariable(required = true) long id) {
		notificationService.makeNotificationAsRead(id);
		return "redirect:/client/profile/tickets/resolved";
	}
}
