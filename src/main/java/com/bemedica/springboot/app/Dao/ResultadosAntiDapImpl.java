package com.bemedica.springboot.app.Dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bemedica.springboot.app.models.entity.ResultadosAnti;

@Service
public class ResultadosAntiDapImpl implements IResultadosAntiDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ResultadosAnti> findAll(Long id) {
		return em.createQuery ("from Resultados where orden_estudio_id="+id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ResultadosAnti> verficarExistencia(Long id) {
		return em.createQuery ("from ResultadosAnti where resultado_id="+id).getResultList();
	}
	
	@Override
	@Transactional
	public void save(ResultadosAnti resultados) {
		if(resultados.getResAntiId()  !=null &&  resultados.getResAntiId()>0) {
			em.merge(resultados);
		}
		else {
			em.persist(resultados);
		}
	}
	
	@Override
	@Transactional(readOnly= true)
	public ResultadosAnti findOne(Long id) {
		return em.find(ResultadosAnti.class,id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Object[]> antibioticos(Long id) {
		List<Object[]> re = null;
    	re= em.createNativeQuery("{call Antibiograma_antibiotocos ("+id+")}").getResultList(); 
    	return re;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Object[]> AntibiogramaNombre (Long id) {
		List<Object[]> re = null;
    	re= em.createNativeQuery("select antibiogramas.anti_id, antibiogramas.anti_nombre\r\n" + 
    			"from resultados_anti, antibiogramas\r\n" + 
    			"where 1=1\r\n" + 
    			"and antibiogramas.anti_id = resultados_anti.anti_id\r\n" + 
    			"GROUP by antibiogramas.anti_id").getResultList(); 
    	return re;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Object[]> ResultadoCultivo(Long linea , Long id) {
		List<Object[]> re = null;
    	re= em.createNativeQuery("{call resultado_cult (" +linea +","+id+")}").getResultList(); 
    	return re;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Object[]> ResultadoAntibio (Long linea , Long id) {
		List<Object[]> re = null;
    	re= em.createNativeQuery("{call resultado_anti (" +linea +","+id+")}").getResultList(); 
    	return re;
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public  String NombreAntibiograma (Long linea , Long id) {
		
		String re= (String) em.createNativeQuery("{call NombreAntibiograma (" +linea +","+id+")}").getSingleResult(); 
    	return re;
	}
}
