package com.bemedica.springboot.app.Dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Orden;

import antlr.collections.List;

@Repository

public class ResultadoPacienteDaoImpl implements IResultadoPacienteDao {
	
	
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	

	@SuppressWarnings ("unchecked")
	
	@Transactional(readOnly= true)
	@Override
	public java.util.List<Orden> findResul(int id) {
		
		return em.createQuery("SELECT  ordenFolio, ordenFecha, monto, orden_id FROM Orden WHERE paciente_id="+id).getResultList();
	}

}
