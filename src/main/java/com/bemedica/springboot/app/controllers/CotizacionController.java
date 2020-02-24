package com.bemedica.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.bemedica.springboot.app.models.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bemedica.springboot.app.service.CotizacionService;
import com.bemedica.springboot.app.service.UserService;

@Controller
public class CotizacionController {
	
	@Autowired
	private CotizacionService cotizacionService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/estudios/cotizaciones", method=RequestMethod.GET)
	public String listarEstudios(Model model, HttpServletRequest request) throws Exception{
	model.addAttribute("cotizaciones",cotizacionService.AppListarEstudios());
	model.addAttribute("user", userService.getSession(request));
	return "estudios/cotizaciones";
	}
	@RequestMapping(value="/enviar_cotizacion",method=RequestMethod.GET)
	public String enviarCoti() {
		return "enviado";
	}

}
