package com.bemedica.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.service.CotizacionService;

@Controller
public class CotizacionController {
	
	@Autowired
	private CotizacionService cotizacionService;
	
	@RequestMapping(value="/estudios/cotizaciones", method=RequestMethod.GET)
	public String listarEstudios(Model model){
		model.addAttribute("cotizaciones",cotizacionService.AppListarEstudios());
		
		return "estudios/cotizaciones";
	}
	@RequestMapping(value="/enviar_cotizacion",method=RequestMethod.GET)
	public String enviarCoti() {
		
		return "enviado";
	}

}
