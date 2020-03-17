package com.bemedica.springboot.app.Dao;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Resultados;

public interface IResultadosWebDao {
	
public List<Resultados> findAll( Long id );
	
	public void save (Resultados resultados);
	
	
	public Resultados findOne (Long id );
	
	public void delete (Long id);
	
	public List <Object []> PacienteOrden (Long id );
	
	public List <Object []> LineasOrden (Long id );
	
	public List <Object []> Estudios (Long id );
	
	public List <Object []> Perfil (Long id );
	
	public List <Object []> Paquete (Long id );
	
	public List <String> ValidarLinea (Long id );
	
	public void Actualizacion_linea (Long id );
	
	public List <String>  ValidarOrden (Long id );
	
	public void Actualizacion_Orden (Long id );
	
	public List <Object []> Resultados (Long id, Long id2, Long id3);
	
	public String NombrePerfil(Long id);
	
	public void UpdateImp(Long id,int con);
	
	public List<Resultados> findAllEstudio( Long id );
	
	public List<Resultados> findAllCultivo( Long id );

	public String NombreVal(Long id);

}
