package com.bemedica.springboot.app.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Resultados;

@Repository

public class ResultadosWebDaoImpl implements IResultadosWebDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")

	@Transactional(readOnly = true)
	@Override
	public List<Resultados> findAll(Long id) {
		return em.createQuery("from Resultados where orden_estudio_id=" + id).getResultList();
	}

	@Override
	@Transactional
	public void save(Resultados resultados) {
		if (resultados.getResultadoId() != null && resultados.getResultadoId() > 0) {
			em.merge(resultados);
		} else {
			em.persist(resultados);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Resultados findOne(Long id) {
		return em.find(Resultados.class, id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> PacienteOrden(Long id) {
		List<Object[]> re = em.createNativeQuery("select \r\n" + "paciente.paciente_id_tex, \r\n"
				+ "concat (persona.persona_nombre,' ',persona.persona_ap,' ',persona.persona_am) as 'paciente',\r\n"
				+ "TIMESTAMPDIFF(YEAR,persona.persona_fecha_na,CURDATE()) as 'años',\r\n"
				+ "timestampdiff(month,persona.persona_fecha_na,curdate())as  'meses', \r\n"
				+ "persona.persona_genero,\r\n" + "orden.orden_fecha, \r\n" + "orden.orden_folio,\r\n"
				+ "ifnull ( (select concat(pm.persona_nombre ,' ', pm.persona_ap ,' ', pm.persona_am) from orden , persona pm , medicos where orden.medico_id= medicos.medico_id \r\n"
				+ "				and medicos.persona_id=pm.persona_id and orden.orden_id=" + id
				+ ") , 'A QUIEN CORRESPONDA' ) as medico,\r\n"
				+ "concat (pe.persona_nombre ,' ', pe.persona_ap,' ', pe.persona_am)  as 'empleado',\r\n"
				+ "orden.orden_estatus, \r\n" + "timestampdiff(day,persona.persona_fecha_na,curdate())as  'dias', \r\n"
				+ "paciente.observacion \r\n"
				+ "from paciente,persona,orden  , persona pe , empleados_sucursal  where \r\n" + "1=1\r\n"
				+ "and orden.paciente_id = paciente.paciente_id\r\n" + "and paciente.persona_id=persona.persona_id\r\n"
				+ "and orden.empleado_id= empleados_sucursal.empleado_id\r\n"
				+ "and empleados_sucursal.persona_id=pe.persona_id\r\n" + "and orden.orden_id=" + id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> LineasOrden(Long id) {

		List<Object[]> re;
		re = em.createNativeQuery("{call LineasOrden (" + id + ")}").getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> Estudios(Long id) {
		List<Object[]> re = em.createNativeQuery("" + "select " + "estudios.estudio_id , "
				+ "estudios.estudio_nombre , " + "estudios.estudio_unidades_res from \r\n" + "estudios \r\n"
				+ "where 1=1\r\n" + "and estudios.estudio_id=" + id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> Perfil(Long id) {
		List<Object[]> re = em.createNativeQuery(
				"select estudios.estudio_id,estudios.estudio_nombre, estudios.estudio_unidades_res, 'estudio' from estudios, perfiles_estudios \r\n"
						+ "where \r\n" + "1=1 \r\n" + "and perfiles_estudios.estudio_id= estudios.estudio_id \r\n"
						+ "and perfiles_estudios.perfil_id=" + id + "\r\n" + "UNION\r\n"
						+ "select estudios.estudio_id,estudios.estudio_nombre, estudios.estudio_unidades_res , 'cultivo'\r\n"
						+ "from estudios, perfiles_cultivos\r\n" + "where \r\n" + "1=1 \r\n"
						+ "and perfiles_cultivos.cultivo_id= estudios.estudio_id\r\n"
						+ "and perfiles_cultivos.perfil_id=" + id)
				.getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> Paquete(Long id) {

		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + "	estudios.estudio_id,\r\n"
				+ "	estudios.estudio_nombre,\r\n" + "	estudios.estudio_unidades_res,\r\n" + "	'null', \r\n"
				+ "	'aux' \r\n" + "FROM\r\n" + "	estudios,\r\n" + "	paquetes_estudios \r\n" + "WHERE\r\n"
				+ "	1 = 1 \r\n" + "	AND paquetes_estudios.estudio_id = estudios.estudio_id \r\n"
				+ "	AND paquetes_estudios.paquete_id = " + id + " " + "UNION ALL\r\n" + "SELECT\r\n"
				+ "	estudios.estudio_id,\r\n" + "	estudios.estudio_nombre,\r\n"
				+ "	estudios.estudio_unidades_res,\r\n" + "	perfiles.perfil_id, \r\n" + "	perfiles.perfil_nombre\r\n"
				+ "FROM\r\n" + "	estudios,\r\n" + "	paquetes_perfiles,\r\n" + "	perfiles_estudios,\r\n"
				+ "	perfiles \r\n" + "WHERE\r\n" + "	1 = 1 \r\n"
				+ "	AND perfiles.perfil_id = perfiles_estudios.perfil_id \r\n"
				+ "	AND paquetes_perfiles.perfil_id = perfiles_estudios.perfil_id \r\n"
				+ "	AND perfiles_estudios.estudio_id = estudios.estudio_id \r\n"
				+ "	AND paquetes_perfiles.paquete_id =" + id + "\r\n" + "GROUP BY\r\n" + "	perfiles.perfil_id\r\n"
				+ "    UNION\r\n" + "    SELECT\r\n" + "	estudios.estudio_id,\r\n"
				+ "	estudios.estudio_nombre,\r\n" + "	estudios.estudio_unidades_res,\r\n" + "	'null', \r\n"
				+ "	'cultivo' \r\n" + "FROM\r\n" + "	estudios,\r\n" + "	paquetes_cultivos \r\n" + "WHERE\r\n"
				+ "	1 = 1 \r\n" + "    and paquetes_cultivos.cultivo_id=estudios.estudio_id\r\n"
				+ "    and paquetes_cultivos.paquete_id=" + id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<String> ValidarLinea(Long id) {
		List<String> re = em
				.createNativeQuery(
						"select resultados.validacion from resultados where resultados.orden_estudio_id=" + id)
				.getResultList();
		return re;
	}

	@Override
	@Transactional
	public void Actualizacion_linea(Long id) {

		em.createQuery("UPDATE OrdenEstudio SET captura = 'Capturado' " + "WHERE orden_estudio_id = " + id)
				.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<String> ValidarOrden(Long id) {

		List<String> re = em
				.createNativeQuery("SELECT orden_estudio.captura from orden_estudio \r\n" + "where 1=1 \r\n"
						+ "and orden_estudio.orden_id =" + id + "\r\n" + "and orden_estudio.captura='Capturado'")
				.getResultList();
		return re;
	}

	@Override
	@Transactional
	public void Actualizacion_Orden(Long id) {
		em.createQuery("UPDATE Orden SET orden_estatus = 'A entrega' " + "WHERE orden_id = " + id).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> Resultados(Long linea, Long linea2, Long linea3) {
		List<Object[]> re = null;
		re = em.createNativeQuery("{call resultado_web (" + linea + ","+ linea2 +","+ linea3 +")}").getResultList();

		return re;
	}

	@Transactional(readOnly = true)
	@Override
	public String NombrePerfil(Long id) {

		String nombre = (String) em
				.createNativeQuery("select perfiles.perfil_nombre from perfiles where perfiles.perfil_id=" + id)
				.getSingleResult();
		return nombre;
	}

	@Transactional(readOnly = true)
	@Override
	public String NombreVal(Long id) {

		String nombreVal = (String) em
				.createNativeQuery("SELECT  concat(\r\n" + "persona_nombre,' ',\r\n" + "persona_ap,' ',\r\n"
						+ "persona_am)\r\n" + "FROM resultados re INNER JOIN user u ON  re.user_id=u.id\r\n" + "\r\n"
						+ " INNER JOIN\r\n" + "empleados_sucursal em ON u.empleado_id=em.empleado_id\r\n"
						+ "INNER JOIN \r\n" + "persona p on p.persona_id=em.persona_id\r\n" + "				  \r\n"
						+ " WHERE re.resultado_id=" + id)
				.getSingleResult();
		return nombreVal;
	}

	@Transactional
	@Override
	public void UpdateImp(Long id, int con) {
		em.createNativeQuery("call UpdateImp (" + id + "," + con + ")").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Resultados> findAllEstudio(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("from Resultados where tipo =1 and orden_estudio_id=" + id).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Resultados> findAllCultivo(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("from Resultados where tipo= 2 and orden_estudio_id=" + id).getResultList();
	}

}
