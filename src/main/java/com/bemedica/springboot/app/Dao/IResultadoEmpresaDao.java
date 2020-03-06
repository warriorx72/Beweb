package com.bemedica.springboot.app.Dao;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Orden;

public interface IResultadoEmpresaDao {
	
	public List<Orden> findResul(int id);
	
	public List<Orden> findOrden (int id, int id2);

} 
