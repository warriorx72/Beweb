package com.bemedica.springboot.app.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComunidadServiceImpl implements ComunidadService{
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Object> AppCitasSitie(){
		return em.createNativeQuery("call app_citas_sitie();").getResultList();
	}

}
