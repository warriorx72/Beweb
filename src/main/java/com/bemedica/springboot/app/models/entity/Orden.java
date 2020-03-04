package com.bemedica.springboot.app.models.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTables;
import javax.persistence.*;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "orden")
///@SecondaryTables({
   /// @SecondaryTable(name="persona", pkJoinColumns={
      //  @PrimaryKeyJoinColumn(name="direccion_id", referencedColumnName="direccion_id") })

//})
public class Orden implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	///private Long id;
	
	public Long orden_id;
	
	@Column(name = "orden_folio")
	public String ordenFolio;
	
	
	public Integer paciente_id;
	public String sucursal_id;
	public String empleado_id;
	public String orden_estatus;
	
	@Column(name = "orden_fecha")
	public String ordenFecha;
    public String orden_urgencia;
    public String orden_cotizacion;
    public String metodo_pago;
    public String pago_final;
    public String fecha_liquidacion;
    public String fecha_cancelacion;
    public String adeudo;
    public Integer promocion_id;
    public String convenio_id;
	public String factura_id;
    public Integer medico_id;
    public String orden_confidencial;
    
    public String monto;
    public String pago_inicial;
    
	public Long getOrden_id() {
		return orden_id;
	}
	public void setOrden_id(Long orden_id) {
		this.orden_id = orden_id;
	}
	public String getOrden_folio() {
		return ordenFolio;
	}
	public void setOrden_folio(String orden_folio) {
		this.ordenFolio = orden_folio;
	}
	public Integer getPaciente_id() {
		return paciente_id;
	}
	public void setPaciente_id(Integer paciente_id) {
		this.paciente_id = paciente_id;
	}
	public String getSucursal_id() {
		return sucursal_id;
	}
	public void setSucursal_id(String sucursal_id) {
		this.sucursal_id = sucursal_id;
	}
	public String getEmpleado_id() {
		return empleado_id;
	}
	public void setEmpleado_id(String empleado_id) {
		this.empleado_id = empleado_id;
	}
	public String getOrden_estatus() {
		return orden_estatus;
	}
	public void setOrden_estatus(String orden_estatus) {
		this.orden_estatus = orden_estatus;
	}
	public String getOrden_fecha() {
		return ordenFecha;
	}
	public void setOrden_fecha(String orden_fecha) {
		this.ordenFecha = orden_fecha;
	}
	public String getOrden_urgencia() {
		return orden_urgencia;
	}
	public void setOrden_urgencia(String orden_urgencia) {
		this.orden_urgencia = orden_urgencia;
	}
	public String getOrden_cotizacion() {
		return orden_cotizacion;
	}
	public void setOrden_cotizacion(String orden_cotizacion) {
		this.orden_cotizacion = orden_cotizacion;
	}
	public String getMetodo_pago() {
		return metodo_pago;
	}
	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}
	public String getPago_final() {
		return pago_final;
	}
	public void setPago_final(String pago_final) {
		this.pago_final = pago_final;
	}
	public String getFecha_liquidacion() {
		return fecha_liquidacion;
	}
	public void setFecha_liquidacion(String fecha_liquidacion) {
		this.fecha_liquidacion = fecha_liquidacion;
	}
	public String getFecha_cancelacion() {
		return fecha_cancelacion;
	}
	public void setFecha_cancelacion(String fecha_cancelacion) {
		this.fecha_cancelacion = fecha_cancelacion;
	}
	public String getAdeudo() {
		return adeudo;
	}
	public void setAdeudo(String adeudo) {
		this.adeudo = adeudo;
	}
	public Integer getPromocion_id() {
		return promocion_id; 
	}
	public void setPromocion_id(Integer promocion_id) {
		this.promocion_id = promocion_id;
	}
	public String getConvenio_id() {
		return convenio_id;
	}
	public void setConvenio_id(String convenio_id) {
		this.convenio_id = convenio_id;
	}
	public String getFactura_id() {
		return factura_id;
	}
	public void setFactura_id(String factura_id) {
		this.factura_id = factura_id;
	}
	public Integer getMedico_id() {
		return medico_id;
	}
	public void setMedico_id(Integer medico_id) {
		this.medico_id = medico_id;
	}
	public String getOrden_confidencial() {
		return orden_confidencial;
	}
	public void setOrden_confidencial(String orden_confidencial) {
		this.orden_confidencial = orden_confidencial;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getPago_inicial() {
		return pago_inicial;
	}
	public void setPago_inicial(String pago_inicial) {
		this.pago_inicial = pago_inicial;
	}
    


	
	
}
