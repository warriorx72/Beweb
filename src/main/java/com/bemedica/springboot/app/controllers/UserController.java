package com.bemedica.springboot.app.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bemedica.springboot.app.service.UserService;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.repository.UserRepository;

@Controller
public class UserController {

	@Autowired // hace una inyeccion y de ahi obtenr set and ge lo que se nececita
	UserService userService;
	@Autowired
	UserRepository repository;
	
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("signup",true);
		model.addAttribute("userForm", new User());
		
		return "/index";
	}
	
	@GetMapping("/prueba")
	public void prueba() throws Exception {
		//
		
	}
	
	
	@PostMapping("/signup")
   	public String postSignup(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		
		
		model.addAttribute("userForm", user);
		model.addAttribute("signup",true);
		
	
		if(result.hasErrors()) {
			return "user-form/user-signup";
		}else {
			try {
				userService.createUser(user);
				} 
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				return "user-form/user-signup";
			}		
		}//End_else
		return "/index";
	}//End
	
	@GetMapping({"/","/index"})
	public String index(Model model,HttpServletRequest request ) throws Exception {
		
		model.addAttribute("user", userService.getSession(request));
		
		return "/index";
	}//End
	
	


	
	@GetMapping("/index2")
	public String getUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
	
	   
		model.addAttribute("userForm", new User());
		
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "index";
	}//End	
	
	@PostMapping("/index")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
		}else {
			try {//Aca tendras error porque este metodo no existe, pero lo crearemos en la siguiente seccion.
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
			}
		}//End_else
		model.addAttribute("userList", userService.getAllUsers());
		return "index";
	}//End
	
	
	

}
