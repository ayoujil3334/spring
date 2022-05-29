package com.pfa.app.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.pfa.app.models.Document;

public interface DocumentService {
	public Document findById(Long id);

	public List<Document> findAll();

	public void create(Document document);

	public void update(Document document);

	public void deleteById(Long id);

	public Page<Document> findAllDocumentByDossierId(int pageNumber, Long id);

	public void upload(MultipartFile file, String userFolder, String name, String extension);
	
	public Resource loadFileAsResource(String userFolder, String fileName);
	
	public List <Document> findAllDocumentByDossierId(Long dossierId);
}
