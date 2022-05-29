package com.pfa.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pfa.app.models.Dossier;

@Repository
public interface DossierRepository extends JpaRepository <Dossier, Long> {
	@Query(value = "select * from dossiers d, types_dossiers td where td.id = d.type_dossier_id and td.type = :typeDossier and d.user_id = :userId", nativeQuery = true)
	public Dossier findDossierByType(@Param("typeDossier") String typeDossier, @Param("userId") Long userId);
}
