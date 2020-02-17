package com.bemedica.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.service.ComunidadService;

@Controller
public class ComunidadController {
	
	
	@Autowired
	private ComunidadService com;
	
	@RequestMapping(value="/comunidad-bemedica", method=RequestMethod.GET)
	public String listarMedicos(Model model) {
		
		model.addAttribute("medico",com.AppCitasSitie());
		return "comunidad-bemedica";
	}

}
