package com.pfa.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pfa.app.models.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
	public Role findByName(String name);
}
