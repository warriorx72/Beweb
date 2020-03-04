package com.bemedica.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bemedica.springboot.app.service.UserService;

@Controller
public class ResultadosController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(value="/resultados/paciente")
	public String paciente(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return"resultados/paciente";
	}
	
	@GetMapping(value="/empleados")
	public String empleados(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return"empleados";
	}
	
	@GetMapping(value="/resultados/empleados")
	public String Resultempleados(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return"resultados/empleados";
	}
}
