package com.pfa.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfa.app.models.TypeDossier;

public interface TypeDossierRepository extends JpaRepository <TypeDossier, Long> {
	
}
