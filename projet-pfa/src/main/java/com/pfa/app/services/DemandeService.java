package com.pfa.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfa.app.models.Demande;

public interface DemandeService {
	public Demande findById(Long id);

	public List<Demande> findAll();

	public void create(Demande demande);

	public void update(Demande demande);

	public void deleteById(Long id);

	public List<Demande> getDemandeByProfesseur(Long professeurId, String status);

	public Page<Demande> getAllDemandesByProfesseurAndStatus(Long professeurId, String status, int pageNumber);

	public Page<Demande> getAllDemandesByStatus(String status, int pageNumber);

	public void changeDemandeStatus(Long idDemande, String status);

	public int getNumberOfAllDemandes();

	public int getNumberOfAllDemandesByStatus(String status);
}
