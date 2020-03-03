package com.bemedica.springboot.app.Dao;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Orden;

public interface IResultadoPacienteDao {


	

	public List<Orden> findResul(int id);

}
