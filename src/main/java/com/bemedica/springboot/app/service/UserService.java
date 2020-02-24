package com.bemedica.springboot.app.service;

import com.bemedica.springboot.app.models.entity.User;

import javax.servlet.http.HttpServletRequest;

import com.bemedica.springboot.app.dto.ChangePasswordForm;


public interface UserService {
	
	public Iterable <User> getAllUsers();
	
	public User createUser(User user) throws Exception;
	public User getUserById(Long id) throws Exception;
	public User updateUser(User user) throws Exception;
	public User changePassword(ChangePasswordForm form) throws Exception;
	public User getSession(HttpServletRequest request) throws Exception; //(aqui "nace" el metodo para implementarlo en ServiceImpl)
	

}
