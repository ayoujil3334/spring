package com.ayoujil.project.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayoujil.project.model.Logiciel;
import com.ayoujil.project.repository.LogicielRepository;

@RestController
@RequestMapping("/api/logiciels")
public class LogicielApi {
	@Autowired
	private LogicielRepository logicielRepository;
	
	@GetMapping("/")
    public List <Logiciel> findAll() {
        return logicielRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Logiciel getById(@PathVariable(required = true) int id) {
        return logicielRepository.findById((long) id).get();
    }
	
	@PostMapping("/")
    public void add(Logiciel logiciel) {
        logicielRepository.save(logiciel);
    }
	
	@DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable(required = true) int id) {
		logicielRepository.deleteById((long) id);
    }
	
	@PutMapping("/")
    public void update(@RequestBody Logiciel logiciel) {
		Logiciel oldLogiciel = logicielRepository.findById(logiciel.getId()).get();
		if(oldLogiciel != null)
		{
			oldLogiciel.setName(logiciel.getName());
			logicielRepository.save(oldLogiciel);
		}
    }
}
