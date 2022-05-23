package com.ayoujil.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ayoujil.project.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository <Ticket, Long> {
	@Query("SELECT t FROM Ticket t WHERE t.status = :status")
    public List<Ticket> getTicketByStatus(@Param("status") String status);
	
	@Query(value = "select t.* from tickets t, users u where t.developper_id = u.id and t.developper_id = :id and t.status = :status", nativeQuery = true)
    public List<Ticket> getTicketByDevelopper(@Param("id") Long id, @Param("status") String status);
	
	@Query(value = "SELECT t.* FROM tickets t, users u WHERE t.client_id = u.id and t.client_id = :id AND t.status = :status", nativeQuery = true)
    public List<Ticket> getTicketByClient(@Param("id") Long id, @Param("status") String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE Ticket t SET t.status = :status WHERE t.id = :id")
    public void changeTicketStatus(@Param("id") Long id, @Param("status") String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE Ticket t SET t.developper.id = :developperId, t.status = 'PENDING' WHERE t.id = :ticketId")
    public void setDevelopper(@Param("developperId") Long developperId, @Param("ticketId") Long ticketId);
	
	@Query(value = "select count(*) from tickets t where t.status = :status", nativeQuery = true)
    public int getNumberOfTicketsByStatus(@Param("status") String status);
    
    @Query(value = "select count(*) from tickets", nativeQuery = true)
    public int getNumberOfAllTickets();
    
    public Ticket findTicketById(Long id);
}
