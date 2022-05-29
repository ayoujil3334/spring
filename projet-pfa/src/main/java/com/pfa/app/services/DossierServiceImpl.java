package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfa.app.models.Dossier;
import com.pfa.app.repositories.DossierRepository;

@Service
public class DossierServiceImpl implements DossierService {
	@Autowired
	DossierRepository dossierRepository;
	
	@Override
	public Dossier findById(Long id) {
		return dossierRepository.findById(id).get();
	}

	@Override
	public List<Dossier> findAll() {
		return dossierRepository.findAll();
	}

	@Override
	public void create(Dossier dossier) {
		dossierRepository.save(dossier);
	}

	@Override
	public void update(Dossier dossier) {
		Dossier oldDossier = dossierRepository.findById(dossier.getId()).get();
		if(oldDossier != null)
		{
			oldDossier.setNom(dossier.getNom());
			oldDossier.setTypeDossier(dossier.getTypeDossier());
			dossierRepository.save(oldDossier);
		}
	}

	@Override
	public void deleteById(Long id) {
		dossierRepository.deleteById(id);
	}

	@Override
	public Dossier findDossierByType(String type, Long userId) {
		return dossierRepository.findDossierByType(type, userId);
	}
}
