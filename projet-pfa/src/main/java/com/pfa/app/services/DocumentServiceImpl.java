package com.pfa.app.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pfa.app.models.Document;
import com.pfa.app.repositories.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {
	@Autowired
	DocumentRepository documentRepository;

	private Path root = null;
	
	private final String GLOBAL_FOLDER = "global_folder/";
	
	@Override
	public Document findById(Long id) {
		return documentRepository.findById(id).get();
	}

	@Override
	public List<Document> findAll() {
		return documentRepository.findAll();
	}

	@Override
	public void create(Document document) {
		documentRepository.save(document);
	}

	@Override
	public void update(Document document) {
		Document oldDocument = documentRepository.findById(document.getId()).get();
		if (oldDocument != null) {
			oldDocument.setNom(document.getNom());
			oldDocument.setExtension(document.getExtension());
			oldDocument.setSize(document.getSize());
			documentRepository.save(oldDocument);
		}
	}

	@Override
	public void deleteById(Long id) {
		documentRepository.deleteById(id);
	}

	@Override
	public Page<Document> findAllDocumentByDossierId(int pageNumber, Long id) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 2);
		return documentRepository.findAllDocumentByDossierId(pageable, id);
	}

	@Override
	public void upload(MultipartFile file, String userFolder, String name, String extension) {
		try {
			root = Paths.get(GLOBAL_FOLDER + userFolder);
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			//System.out.println("fichier: " + file.getOriginalFilename());
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	@Override
	public Resource loadFileAsResource(String userFolder, String fileName) {
        try {
        	this.root = root = Paths.get(GLOBAL_FOLDER + userFolder);
        	
        	//Files.createDirectories(this.root);
        	
            Path filePath = this.root.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                //throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            //throw new Exception("File not found " + fileName, ex);
        }
		return null;
    }

	@Override
	public List<Document> findAllDocumentByDossierId(Long dossierId) {
		return documentRepository.findAllDocumentByDossierId(dossierId);
	}
}
