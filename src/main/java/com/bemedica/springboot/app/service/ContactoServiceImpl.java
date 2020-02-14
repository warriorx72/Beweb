package com.bemedica.springboot.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Contacto;
import com.bemedica.springboot.app.repository.ContactoRepository;

@Service
public class ContactoServiceImpl implements ContactoService{
	
	@Autowired
	private ContactoRepository repository;
	
	@Override
	@Transactional
	public void save(Contacto contacto) {
		repository.save(contacto);
	}

	
}