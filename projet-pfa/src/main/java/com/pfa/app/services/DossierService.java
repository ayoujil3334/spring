package com.pfa.app.services;

import java.util.List;

import com.pfa.app.models.Dossier;

public interface DossierService {
	public Dossier findById(Long id);

	public List<Dossier> findAll();

	public void create(Dossier dossier);

	public void update(Dossier dossier);

	public void deleteById(Long id);
	
	public Dossier findDossierByType(String type, Long userId);
}
