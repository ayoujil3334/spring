package com.pfa.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pfa.app.models.Specialite;
import com.pfa.app.models.User;
import com.pfa.app.services.SpecialiteService;

@Controller
public class SpecialiteController {
	@Autowired
	SpecialiteService specialiteService;
	
	public void delete(Long id)
	{
		specialiteService.deleteById(id);
	}
}
