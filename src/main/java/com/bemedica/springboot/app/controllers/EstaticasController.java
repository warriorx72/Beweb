package com.bemedica.springboot.app.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.models.entity.Contacto;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.service.ContactoService;
import com.bemedica.springboot.app.service.UserService;

@Controller
public class EstaticasController {
	
	@Autowired
	ContactoService contactoService;
	@Autowired
	UserService userService;
	
	@GetMapping(value="/acerca-de-nosotros")
	public String nosotros(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return"acerca-de-nosotros";
	}
	
	@RequestMapping(value = "/contacto", method=RequestMethod.GET)
	public String contactar(Model model, Map<String, Object> m, HttpServletRequest request ) throws Exception {
	Contacto contacto= new Contacto();
	m.put("contacto", contacto);
	model.addAttribute("user", userService.getSession(request)); //(model que se llamo del metodo para poner el nombre del usuario logueado)
	return "contacto";
	}
	
	@RequestMapping(value="/contacto_enviar",method = RequestMethod.POST)
	public String guardarContacto(Model model, Map<String, Object> m,Contacto contacto){
		contactoService.save(contacto);
		m.put("contacto", contacto);
		
		return "redirect:contacto";
	}
	@GetMapping(value="/bolsa-de-trabajo")
	public String enviado(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return"bolsa-de-trabajo";
	}
	@GetMapping(value="/politica-de-calidad")
	public String poli(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return "politica-de-calidad";
	}
	@GetMapping(value="/certificaciones")
	public String certificado(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return "certificaciones";
	}
	@GetMapping(value="/estudios/informacion")
	public String info(Model model, HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return "estudios/informacion";
	}
	@GetMapping(value="/pacientes")
	public String pacientes(Model model ,HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return "pacientes";
	}
	@GetMapping(value="/atencion-empresas")
	public String ateemp(Model model, HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));	
	return "atencion-empresas";
	}

	@GetMapping(value="/servicios")
	public String servicios(Model model, HttpServletRequest request) throws Exception{
	model.addAttribute("user", userService.getSession(request));
	return "servicios";
	}

}
