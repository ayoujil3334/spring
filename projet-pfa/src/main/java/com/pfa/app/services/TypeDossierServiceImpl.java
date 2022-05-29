package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pfa.app.models.TypeDossier;
import com.pfa.app.repositories.TypeDossierRepository;

@Service
public class TypeDossierServiceImpl implements TypeDossierService {
	@Autowired
	TypeDossierRepository typeDossierRepository;

	@Override
	public TypeDossier findById(Long id) {
		return typeDossierRepository.findById(id).get();
	}

	@Override
	public List<TypeDossier> findAll() {
		return typeDossierRepository.findAll();
	}

	@Override
	public void create(TypeDossier typeDossier) {
		typeDossierRepository.save(typeDossier);
	}

	@Override
	public void update(TypeDossier typeDossier) {
		TypeDossier oldTypeDossier = typeDossierRepository.findById(typeDossier.getId()).get();
		if(oldTypeDossier != null) {
			oldTypeDossier.setType(typeDossier.getType());
			typeDossierRepository.save(oldTypeDossier);
		}
		
		
	}

	@Override
	public void deleteById(Long id) {
		typeDossierRepository.deleteById(id);
	}

	@Override
	public Page<TypeDossier> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1,6);
	    return typeDossierRepository.findAll(pageable);
	}

}
