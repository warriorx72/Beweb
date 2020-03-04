package com.bemedica.springboot.app.controllers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bemedica.springboot.app.Dao.IResultadoPacienteDao;
import com.bemedica.springboot.app.service.UserService;


@Controller
public class PacienteController {
	
	@Autowired 
	
	
	UserService userService;
	

	@Autowired
	private IResultadoPacienteDao ResultadoPacienteDao;
	
	
	@Autowired
	EntityManager em;
	
	
	@RequestMapping (value="/resultados/paciente/{id}", method= RequestMethod.GET)
	public String listarResul(@PathVariable (value ="id") int id, Model model, HttpServletRequest request ) throws Exception  {
		System.out.println("hgjhjhj");
		
		System.out.println("aqui esta el bendito id"+ id);
	
		model.addAttribute("user", userService.getSession(request));
		model.addAttribute("vista", ResultadoPacienteDao.findResul(id));
		
	System.out.println("ya estoy hasta webo"+ResultadoPacienteDao.findResul(id));
		return "resultados/paciente";
	}
	
	
	

	
	
	
	
	
	
	
	

}
