package com.ayoujil.project.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ayoujil.project.model.Ticket;

public interface TicketService {
	public void save(Ticket ticket);

	public List<Ticket> findAll();

	public Ticket findById(int id);

	public void deleteById(int id);

	public void update(Ticket ticket);
	
	public List<Ticket> getTicketByStatus(String status);
	
	public List<Ticket> getTicketByDevelopper(Long id, String status);
	
	public List<Ticket> getTicketByClient(Long id, String status);
	
	public void changeTicketStatus(Long id, String status);
	
	public void setDevelopper(Long developperId, Long ticketId);
	
	public int getNumberOfTicketsByStatus(String status);
	
	public int getNumberOfAllTickets();
	
	public Ticket findTicketById(Long id);
}
