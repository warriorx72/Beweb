package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resultados")
public class Resultados implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="resultado_id")
	private Long ResultadoId;
	
	
	@Column(name="orden_estudio_id")
	private Long OrdenEstudioId;
	
	@Column(name="resultado_cuanti")
	private String ResultadoCuanti;
	
	@Column(name="resultado_cuali")
	private String ResultadoCuali;

	@Column(name="validacion")
	private String Validacion;
	
	@Column(name="estudio_id")
	private Long estudio_id;
	
	@Column(name="estudio_nombre")
	private String  EstudioNombre;
	
	@Column(name="comentario")
	private String  Comentario;

	@Column(name="perfil")
	private String  perfil; 
	
	@Column(name="tipo")
	private String  tipo;
	
	@Column(name="impresion")
	private Boolean Impresion; 
	
	
	@Column(name="antibiograma_id")
	private Long AntiIdR; 
		
	
	@Column(name="user_id")
	private Long user_id; 
		
	
	
	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getImpresion() {
		return Impresion;
	}

	public void setImpresion(Boolean impresion) {
		Impresion = impresion;
	}

	public Long getResultadoId() {
		return ResultadoId;
	}

	public void setResultadoId(Long resultadoId) {
		ResultadoId = resultadoId;
	}

	public Long getOrdenEstudioId() {
		return OrdenEstudioId;
	}

	public void setOrdenEstudioId(Long ordenEstudioId) {
		OrdenEstudioId = ordenEstudioId;
	}

	public String getResultadoCuanti() {
		return ResultadoCuanti;
	}

	public void setResultadoCuanti(String resultadoCuanti) {
		ResultadoCuanti = resultadoCuanti;
	}

	public String getResultadoCuali() {
		return ResultadoCuali;
	}

	public void setResultadoCuali(String resultadoCuali) {
		ResultadoCuali = resultadoCuali;
	}


	public String getValidacion() {
		return Validacion;
	}

	public void setValidacion(String validacion) {
		Validacion = validacion;
	}

	public Long getEstudio_id() {
		return estudio_id;
	}

	public void setEstudio_id(Long estudio_id) {
		this.estudio_id = estudio_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEstudioNombre() {
		return EstudioNombre;
	}

	public void setEstudioNombre(String estudioNombre) {
		EstudioNombre = estudioNombre;
	}

	public String getComentario() {
		return Comentario;
	}

	public void setComentario(String comentario) {
		Comentario = comentario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getAntiIdR() {
		return AntiIdR;
	}

	public void setAntiIdR(Long antiIdR) {
		AntiIdR = antiIdR;
	}

	
	
	
	

}
