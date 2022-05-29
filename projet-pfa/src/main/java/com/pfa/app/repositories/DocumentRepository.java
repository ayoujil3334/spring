package com.pfa.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfa.app.models.Document;
import com.pfa.app.models.Dossier;

public interface DocumentRepository extends JpaRepository <Document, Long> {
	@Query("select d from Document d where d.dossier.id = :dossierId")
	public Page <Document> findAllDocumentByDossierId(Pageable pageable, @Param("dossierId") Long dossierId);
	
	@Query("select d from Document d where d.dossier.id = :dossierId")
	public List <Document> findAllDocumentByDossierId(@Param("dossierId") Long dossierId);
}
