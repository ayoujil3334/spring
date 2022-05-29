package com.pfa.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pfa.app.models.Specialite;

public interface SpecialiteService {
	public Specialite findById(Long id);
	public void create(Specialite specialite);
	public void update(Specialite specialite);
	public void deleteById(Long id);
	public Page<Specialite> findAll(int pageNumber);
	public List<Specialite> findAll();
}
