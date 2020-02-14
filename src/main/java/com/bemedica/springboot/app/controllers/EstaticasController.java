package com.bemedica.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.models.entity.Contacto;
import com.bemedica.springboot.app.service.ContactoService;

@Controller
public class EstaticasController {
	
	@Autowired
	ContactoService contactoService;
	
	@GetMapping(value="/acerca-de-nosotros")
	public String nosotros(Model model){
		return"acerca-de-nosotros";
	}
	
	@RequestMapping(value = "/contacto", method=RequestMethod.GET)
	public String contactar(Model model, Map<String, Object> m) {
		Contacto contacto= new Contacto();
		m.put("contacto", contacto);
		return "contacto";
	}
	
	@RequestMapping(value="/contacto_enviar",method = RequestMethod.POST)
	public String guardarContacto(Model model, Map<String, Object> m,Contacto contacto){
		contactoService.save(contacto);
		m.put("contacto", contacto);
		
		return "redirect:contacto";
	}
	@GetMapping(value="/bolsa-de-trabajo")
	public String enviado(Model model){
		return"bolsa-de-trabajo";
	}
	@GetMapping(value="/politica-de-calidad")
	public String poli(Model model){
		return "politica-de-calidad";
	}
	@GetMapping(value="/certificaciones")
	public String certificado(Model model){
		return "certificaciones";
	}
	@GetMapping(value="/estudios/informacion")
	public String info(Model model){
		return "estudios/informacion";
	}
	@GetMapping(value="/pacientes")
	public String pacientes(Model model){
		return "pacientes";
	}
	@GetMapping(value="/atencion-empresas")
	public String ateemp(Model model){
		return "atencion-empresas";
	}
	@GetMapping(value="/comunidad-bemedica")
	public String comubeme(Model model){
		return "comunidad-bemedica";
	}

	@GetMapping(value="/servicios")
	public String servicios(Model model){
		return "servicios";
	}

}
