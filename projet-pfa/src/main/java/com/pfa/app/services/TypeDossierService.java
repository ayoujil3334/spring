package com.pfa.app.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pfa.app.models.TypeDossier;

public interface TypeDossierService {
	public TypeDossier findById(Long id);

	public List<TypeDossier> findAll();

	public void create(TypeDossier typeDossier);

	public void update(TypeDossier typeDossier);

	public void deleteById(Long id);

	public Page<TypeDossier> findAll(int currentPage);
}
