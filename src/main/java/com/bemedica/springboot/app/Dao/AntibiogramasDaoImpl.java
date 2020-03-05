package com.bemedica.springboot.app.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Antibiogramas;
@Service
public class AntibiogramasDaoImpl implements IAntibiogramasDao {

	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Antibiogramas> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery ("from Antibiogramas").getResultList();
	}

	@Override
	@Transactional
	public void save(Antibiogramas antibiogramas) {
		// TODO Auto-generated method stub
		if(antibiogramas.getAntiId() !=null &&  antibiogramas.getAntiId()>0) {
			em.merge(antibiogramas);
			
		}
		else {
			em.persist(antibiogramas);
		}
	}

	@Override
	@Transactional
	public Antibiogramas findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Antibiogramas.class,id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}

}
