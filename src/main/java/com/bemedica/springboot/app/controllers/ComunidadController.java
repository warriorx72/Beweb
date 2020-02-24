package com.bemedica.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.service.ComunidadService;
import com.bemedica.springboot.app.service.UserService;

@Controller
public class ComunidadController {
	
	
	@Autowired
	private ComunidadService com;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/comunidad-bemedica", method=RequestMethod.GET)
	public String listarMedicos(Model model ,HttpServletRequest request) throws Exception {
	model.addAttribute("medico",com.AppCitasSitie());
	model.addAttribute("user", userService.getSession(request));
	return "comunidad-bemedica";
	}

}
