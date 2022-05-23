package com.ayoujil.project.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoujil.project.model.Ticket;
import com.ayoujil.project.repository.TicketRepository;

@RestController
@RequestMapping("/api/tickets")
public class TicketApi {
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping("/")
    public List <Ticket> findAll() {
        return ticketRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Ticket getById(@PathVariable(required = true) int id) {
        return ticketRepository.findById((long) id).get();
    }
	
	@PostMapping("/")
    public void add(Ticket logiciel) {
        ticketRepository.save(logiciel);
    }
	
	@DeleteMapping("/{id}")
    public void deleteById(@PathVariable(required = true) int id) {
		ticketRepository.deleteById((long) id);
    }
	
	@PutMapping("/")
    public void update(@RequestBody Ticket ticket) {
		Ticket oldTicket = ticketRepository.findById(ticket.getId()).get();
		if(oldTicket != null)
		{
			
			ticketRepository.save(oldTicket);
		}
    }
}