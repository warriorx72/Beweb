package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="antibiogramas")
public class Antibiogramas implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="anti_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AntiId;
	
	@Column(name="anti_nombre")
	private String AntiNombre;
	
	@Column(name="anti_individual")
	private Boolean AntiIndividual;
	
	@Column(name="anti_impresion")
	private String AntiImpresion;
	
	@Column(name="anti_descripcion")
	private String AntiDescripcion;
	
	@Column(name="anti_id_text")
	private String AntiIdText;
	
	@Column(name="anti_estatus")
	private boolean AntiEstatus;


	public boolean isAntiEstatus() {
		return AntiEstatus;
	}

	public void setAntiEstatus(boolean antiEstatus) {
		AntiEstatus = antiEstatus;
	}

	public Long getAntiId() {
		return AntiId;
	}

	public void setAntiId(Long antiId) {
		AntiId = antiId;
	}

	public String getAntiNombre() {
		return AntiNombre;
	}

	public void setAntiNombre(String antiNombre) {
		AntiNombre = antiNombre;
	}

	public Boolean getAntiIndividual() {
		return AntiIndividual;
	}

	public void setAntiIndividual(Boolean antiIndividual) {
		AntiIndividual = antiIndividual;
	}

	public String getAntiImpresion() {
		return AntiImpresion;
	}

	public void setAntiImpresion(String antiImpresion) {
		AntiImpresion = antiImpresion;
	}

	public String getAntiDescripcion() {
		return AntiDescripcion;
	}

	public void setAntiDescripcion(String antiDescripcion) {
		AntiDescripcion = antiDescripcion;
	}

	public String getAntiIdText() {
		return AntiIdText;
	}

	public void setAntiIdText(String antiIdText) {
		AntiIdText = antiIdText;
	}
	
	

}
