package com.ayoujil.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayoujil.project.model.Ticket;
import com.ayoujil.project.model.User;
import com.ayoujil.project.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	TicketRepository ticketRepository;

	@Override
	public void save(Ticket ticket) {
		ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public Ticket findById(int id) {
		return ticketRepository.findById((long) id).get();
	}

	@Override
	public void deleteById(int id) {
		ticketRepository.deleteById((long) id);
	}

	@Override
	public void update(Ticket ticket) {
		Ticket oldTicket = ticketRepository.findById(ticket.getId()).get();
		if(oldTicket != null)
		{
			oldTicket.setDescription(ticket.getDescription());
			oldTicket.setEnvironment(ticket.getEnvironment());
			oldTicket.setPriority(ticket.getPriority());
			oldTicket.setStatus(ticket.getStatus());
			oldTicket.setDevelopper(ticket.getDevelopper());
			ticketRepository.save(oldTicket);
		}
	}
	
	@Override
	public List<Ticket> getTicketByStatus(String status)
	{
		return ticketRepository.getTicketByStatus(status);
	}

	@Override
	public List<Ticket> getTicketByDevelopper(Long id, String status) {
		return ticketRepository.getTicketByDevelopper(id, status);
	}

	@Override
	public List<Ticket> getTicketByClient(Long id, String status) {
		return ticketRepository.getTicketByClient(id, status);
	}

	@Override
	public void changeTicketStatus(Long id, String status) {
		ticketRepository.changeTicketStatus(id, status);
	}

	@Override
	public void setDevelopper(Long developperId, Long ticketId) {
		ticketRepository.setDevelopper(developperId, ticketId);
	}

	@Override
	public int getNumberOfTicketsByStatus(String status) {
		return ticketRepository.getNumberOfTicketsByStatus(status);
	}

	@Override
	public int getNumberOfAllTickets() {
		return ticketRepository.getNumberOfAllTickets();
	}

	@Override
	public Ticket findTicketById(Long id) {
		return ticketRepository.findTicketById(id);
	}
}
