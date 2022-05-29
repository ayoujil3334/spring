package com.pfa.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfa.app.models.Document;
import com.pfa.app.models.TypeDocument;

public interface TypeDocumentRepository extends JpaRepository <TypeDocument, Long> {
	@Query("select t from TypeDocument t where t.typeDossier.id = :idTypeDossier")
	public List <TypeDocument> findByTypeDossier(@Param("idTypeDossier") Long idTypeDossier);
}
