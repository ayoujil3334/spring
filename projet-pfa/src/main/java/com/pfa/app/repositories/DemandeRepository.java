package com.pfa.app.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfa.app.models.Demande;

public interface DemandeRepository extends JpaRepository <Demande, Long> {
	@Query(value = "select * from demandes where professeur_id = :professeurId and status = :status", nativeQuery = true)
	public List <Demande> getDemandeByProfesseurStatus(@Param("professeurId") Long professeurId, @Param("status") String status);
	
	@Query("select d from Demande d where d.professeur.id = :professeurId and d.status = :status")
	public Page <Demande> getAllDemandesByProfesseurAndStatus(Pageable pageable, @Param("professeurId") Long professeurId, @Param("status") String status);
	
	@Query("select d from Demande d where d.status = :status")
	public Page <Demande> getAllDemandesByStatus(Pageable pageable, @Param("status") String status);
	
	@Modifying
	@Transactional
	@Query("update Demande d set d.status = :status where d.id = :idDemande")
	public void changeDemandeStatus(@Param("idDemande") Long idDemande, @Param("status") String status);
	
	@Query(value = "select count(*) from demandes", nativeQuery = true)
    public int getNumberOfAllDemandes();
	
	@Query(value = "select count(*) from demandes d where d.status = :status", nativeQuery = true)
    public int getNumberOfAllDemandesByStatus(@Param("status") String status);
}
