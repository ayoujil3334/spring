package com.pfa.app.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pfa.app.models.Document;
import com.pfa.app.models.TypeDocument;

public interface TypeDocumentService {
	public TypeDocument findById(Long id);

	public List<TypeDocument> findAll();

	public void create(TypeDocument typeDocument);

	public void update(TypeDocument typeDocument);

	public void deleteById(Long id);

	public Page<TypeDocument> findAllTypesDocuments(int pageNumber);

	public List<TypeDocument> findByTypeDossier(Long id);
}
