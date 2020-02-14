package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="web_contacto")
public class Contacto implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contacto_id;
	
	@Column
	private String nombrecompleto;
	
	@Column
	private String motivo;
	
	@Column
	private String telefono;
	
	@Column
	private String mensaje;
	
	@Column
	private String correo;
	
	@CreationTimestamp
	private LocalDateTime fecha_mensaje;

	public Long getContacto_id() {
		return contacto_id;
	}

	public void setContacto_id(Long contacto_id) {
		this.contacto_id = contacto_id;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public LocalDateTime getFecha_mensaje() {
		return fecha_mensaje;
	}

	public void setFecha_mensaje(LocalDateTime fecha_mensaje) {
		this.fecha_mensaje = fecha_mensaje;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
