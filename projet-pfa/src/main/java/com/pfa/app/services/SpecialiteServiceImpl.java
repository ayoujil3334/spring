package com.pfa.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pfa.app.models.Specialite;
import com.pfa.app.repositories.SpecialiteRepository;

@Service
public class SpecialiteServiceImpl implements SpecialiteService {
	@Autowired
	SpecialiteRepository specialiteRepository;

	@Override
	public Specialite findById(Long id) {
		return specialiteRepository.findById(id).get();
	}

	@Override
	public Page<Specialite> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1,6);
	    return specialiteRepository.findAll(pageable);
	}

	@Override
	public void create(Specialite specialite) {
		specialiteRepository.save(specialite);
	}

	@Override
	public void update(Specialite specialite) {
		Specialite oldSpecialite = specialiteRepository.findById(specialite.getId()).get();
		if(oldSpecialite != null)
		{
			oldSpecialite.setName(specialite.getName());
			specialiteRepository.save(oldSpecialite);
		}
	}

	@Override
	public void deleteById(Long id) {
		specialiteRepository.deleteById(id);
	}

	@Override
	public List<Specialite> findAll() {
		return specialiteRepository.findAll();
	}

}
