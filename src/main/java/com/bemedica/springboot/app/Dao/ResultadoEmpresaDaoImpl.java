package com.bemedica.springboot.app.Dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bemedica.springboot.app.models.entity.Orden;

@Service
public class ResultadoEmpresaDaoImpl implements IResultadoEmpresaDao {

	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Orden> findResul(int id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(
				"select paciente_id_tex AS Folio, paciente_id, CONCAT(persona_nombre, \" \", persona_ap,\" \", persona_am) AS Nombre, persona_tel_casa  \r\n"
						+ "from paciente_vista where empresa_id =" + id)
				.getResultList();
	}
 
	@SuppressWarnings("unchecked")
	public List<Orden> findOrden(int id, int id2) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("select orden_folio, paciente_id, orden_fecha, monto, orden_id from\r\n" + 
				"				(select o.orden_folio , pv.paciente_id, o.orden_id, o.orden_estatus, o.orden_fecha, o.monto, paciente_id_tex AS Folio, CONCAT(persona_nombre, \" \", persona_ap,\" \", persona_am) AS Nombre, persona_tel_casa\r\n" + 
				"				from paciente_vista as pv\r\n" + 
				"				INNER JOIN orden o on pv.paciente_id=o.paciente_id\r\n" + 
				"				where empresa_id ="+id+") AS pacs\r\n" + 
				"				where pacs.paciente_id="+id2+"\r\n" + 
				"				and pacs.orden_estatus = \"Finalizada\"").getResultList();
	}

}
