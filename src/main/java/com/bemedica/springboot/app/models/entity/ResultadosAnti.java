package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resultados_anti")
public class ResultadosAnti implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="res_anti_id")
	private Long ResAntiId;
	
	@Column(name="resultado_id")
	private Long ResultadoId;
	
	@Column(name="anti_id")
	private Long AntiId;
	
	@Column(name="resultado")
	private String Resultado;
	
	@Column(name="antibiotico_id")
	private Long AntibioticoId;
	
	@Column(name="antibiotico_nombre")
	private String AntibioticoNombre;
	
	public Long getResAntiId() {
		return ResAntiId;
	}

	public void setResAntiId(Long resAntiId) {
		ResAntiId = resAntiId;
	}

	public Long getResultadoId() {
		return ResultadoId;
	}

	public void setResultadoId(Long resultadoId) {
		ResultadoId = resultadoId;
	}

	public Long getAntiId() {
		return AntiId;
	}

	public void setAntiId(Long antiId) {
		AntiId = antiId;
	}

	public String getResultado() {
		return Resultado;
	}

	public void setResultado(String resultado) {
		Resultado = resultado;
	}

	public Long getAntibioticoId() {
		return AntibioticoId;
	}

	public void setAntibioticoId(Long antibioticoId) {
		AntibioticoId = antibioticoId;
	}

	public String getAntibioticoNombre() {
		return AntibioticoNombre;
	}

	public void setAntibioticoNombre(String antibioticoNombre) {
		AntibioticoNombre = antibioticoNombre;
	}
	
	
	
}
