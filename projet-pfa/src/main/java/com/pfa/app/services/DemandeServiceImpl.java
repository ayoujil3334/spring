package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pfa.app.models.Demande;
import com.pfa.app.repositories.DemandeRepository;

@Service
public class DemandeServiceImpl implements DemandeService {
	@Autowired
	DemandeRepository demandeRepository;
	
	@Override
	public Demande findById(Long id) {
		return demandeRepository.findById(id).get();
	}

	@Override
	public List<Demande> findAll() {
		return demandeRepository.findAll();
	}

	@Override
	public void create(Demande demande) {
		demandeRepository.save(demande);
	}

	@Override
	public void update(Demande demande) {
		Demande oldDemande = demandeRepository.findById(demande.getId()).get();
		if(oldDemande != null)
		{
			oldDemande.setDateCreation(demande.getDateCreation());
			oldDemande.setStatus(demande.getStatus());
			oldDemande.setProfesseur(demande.getProfesseur());
			demandeRepository.save(oldDemande);
		}
	}

	@Override
	public void deleteById(Long id) {
		demandeRepository.deleteById(id);
	}

	@Override
	public List<Demande> getDemandeByProfesseur(Long professeurId, String status) {
		return demandeRepository.getDemandeByProfesseurStatus(professeurId, status);
	}

	@Override
	public Page<Demande> getAllDemandesByProfesseurAndStatus(Long professeurId, String status, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
	    return demandeRepository.getAllDemandesByProfesseurAndStatus(pageable, professeurId, status);
	}

	@Override
	public Page<Demande> getAllDemandesByStatus(String status, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
	    return demandeRepository.getAllDemandesByStatus(pageable, status);
	}

	@Override
	public void changeDemandeStatus(Long idDemande, String status) {
		demandeRepository.changeDemandeStatus(idDemande, status);
	}

	@Override
	public int getNumberOfAllDemandes() {
		return demandeRepository.getNumberOfAllDemandes();
	}

	@Override
	public int getNumberOfAllDemandesByStatus(String status) {
		return demandeRepository.getNumberOfAllDemandesByStatus(status);
	}
}
