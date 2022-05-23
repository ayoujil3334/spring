package com.ayoujil.project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ayoujil.project.model.Logiciel;

public interface LogicielService {
	public void save(Logiciel logiciel);

	public List<Logiciel> findAll();

	public Logiciel findById(int id);

	public void deleteById(int id);

	public void update(Logiciel logiciel);
	
	public Page<Logiciel> findPage(int pageNumber);
}
