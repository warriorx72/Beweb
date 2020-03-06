package com.bemedica.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.Dao.IResultadoEmpresaDao;
import com.bemedica.springboot.app.service.UserService;

@Controller
public class ResultadosController {
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	IResultadoEmpresaDao resultadoEmpresa;
	
	
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
	
	
	

	@RequestMapping(value = "/resultados/empresa/{id}", method = RequestMethod.GET)
	public String listarResul(@PathVariable(value = "id") int id, Model model, HttpServletRequest request)
			throws Exception {

		model.addAttribute("user", userService.getSession(request));
		model.addAttribute("vista", resultadoEmpresa.findResul(id));

		System.out.println(id);
		return "resultados/empresa";
	} 
	
	

	@RequestMapping(value = "/orden-empresa/{id}/{id2}")
	public String listarResulporOrden(@PathVariable(value = "id") int id, @PathVariable(value = "id2") int id2,
			Model model, HttpServletRequest request) throws Exception {

		model.addAttribute("user", userService.getSession(request));
		model.addAttribute("vistaordenes", resultadoEmpresa.findOrden(id, id2));
		System.out.println(id +" "+ id2);

		return "resultados/orden-empresa";
	}
}
