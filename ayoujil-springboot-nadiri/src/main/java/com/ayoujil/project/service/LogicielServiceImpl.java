package com.ayoujil.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.model.User;
import com.ayoujil.project.repository.LogicielRepository;
import com.ayoujil.project.repository.UserRepository;

@Service
public class LogicielServiceImpl implements LogicielService {
	@Autowired
	LogicielRepository logicielRepository;

	@Override
	public void save(Logiciel logiciel) {
		logicielRepository.save(logiciel);
	}

	@Override
	public List<Logiciel> findAll() {
		return logicielRepository.findAll();
	}

	@Override
	public Logiciel findById(int id) {
		return logicielRepository.findById((long) id).get();
	}

	@Override
	public void deleteById(int id) {
		logicielRepository.deleteById((long) id);
	}

	@Override
	public void update(Logiciel logiciel) {
		Logiciel oldLogiciel = logicielRepository.findById(logiciel.getId()).get();
		if(oldLogiciel != null)
		{
			oldLogiciel.setName(logiciel.getName());
			logicielRepository.save(oldLogiciel);
		}
	}
	
	@Override
	public Page<Logiciel> findPage(int pageNumber){
	    Pageable pageable = PageRequest.of(pageNumber - 1,6);
	    return logicielRepository.findAll(pageable);
	}
}
