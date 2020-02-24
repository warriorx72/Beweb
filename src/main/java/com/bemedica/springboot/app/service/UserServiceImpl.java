package com.bemedica.springboot.app.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bemedica.springboot.app.dto.ChangePasswordForm;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.repository.UserRepository;
import com.bemedica.springboot.app.exception.CustomeFieldValidationException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; //agregue private para resolver el problema del 19-02-20
	
	@Autowired
	UserRepository repository;

	

	@Override
	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}
	
	private boolean checkEmailAvailable(User user) throws Exception {
		User userFound = repository.findByUserEmail(user.getUserEmail());
		if (userFound == null) {
			throw new CustomeFieldValidationException("Correo no disponible","email");
		}//End_if
		return true;
	}//End_Exception

	@Override
	public User createUser(User user) throws Exception {
		if (checkEmailAvailable(user) && checkUserNickAvailable(user) || user.getUserId()>0 || user.getUserId()!=null) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getUserPassword());
			user.setUserPassword(encodePassword);
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getUserId());
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());

		if ( !user.getUserPassword().equals(form.getCurrentPassword())) {
			throw new Exception ("Current Password invalido.");
		}
		
		if( user.getUserPassword().equals(form.getNewPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Current Password no coinciden.");
		}
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setUserPassword(encodePassword);
		return repository.save(user);
	}
	
	public User getSession(HttpServletRequest request) throws Exception  //metodo que se hizo para poner el nombre del usurio logueado y ponerlo en cada template el (model) 
	{
		com.bemedica.springboot.app.models.entity.User u  = new User();
		//rescata el nombre de la sesion
		try 
		{
			if(request.getUserPrincipal().getName().toString() != null)
			{
				System.out.println("Hay Sesion");
				u = repository.findByUserName(request.getUserPrincipal().getName().toString()).orElseThrow(() -> new UsernameNotFoundException("Login Username Invalido."));
			}
			
		}
		catch(Exception e) 
		{
			System.out.println("No hay Sesion");
			u.setUserName("No_Session");
			u.setUserNombre("No_Session");
		}
		//cierra
		return u;
	}
	
	private boolean checkUserNickAvailable(User user) throws Exception{
		Optional<User> userFound = repository.findByUserName(user.getUserName());
		if(userFound.isPresent()) {
			throw new CustomeFieldValidationException("Usuario no disponible","usernick");
		}
		return true;
	}
	
	
	protected void mapUser(User from,User to) {
		to.setUserEmail(from.getUserEmail());
		to.setUserFirstName(from.getUserFirstName());
		to.setUserLastName(from.getUserLastName());
		to.setUserName(from.getUserName());
		to.setUserNombre(from.getUserNombre());
		///to.setRoles(from.getRoles());
		to.setUserId(from.getUserId());
	}
	


}
