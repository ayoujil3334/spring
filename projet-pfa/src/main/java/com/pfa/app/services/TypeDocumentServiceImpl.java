package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pfa.app.models.Document;
import com.pfa.app.models.TypeDocument;
import com.pfa.app.repositories.TypeDocumentRepository;

@Service
public class TypeDocumentServiceImpl implements TypeDocumentService{
	@Autowired
	TypeDocumentRepository typeDocumentRepository;

	@Override
	public TypeDocument findById(Long id) {
		return typeDocumentRepository.findById(id).get();
	}

	@Override
	public List<TypeDocument> findAll() {
		return typeDocumentRepository.findAll();
	}

	@Override
	public void create(TypeDocument typeDocument) {
		typeDocumentRepository.save(typeDocument);
	}

	@Override
	public void update(TypeDocument typeDocument) {
		TypeDocument oldTypeDocument = typeDocumentRepository.findById(typeDocument.getId()).get();
		if(oldTypeDocument != null)
		{
			oldTypeDocument.setType(typeDocument.getType());
			typeDocumentRepository.save(oldTypeDocument);
		}
		
	}

	@Override
	public void deleteById(Long id) {
		typeDocumentRepository.deleteById(id);
	}

	@Override
	public Page<TypeDocument> findAllTypesDocuments(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1,6);
	    return typeDocumentRepository.findAll(pageable);
	}

	@Override
	public List<TypeDocument> findByTypeDossier(Long id) {
		return typeDocumentRepository.findByTypeDossier(id);
	}
}
