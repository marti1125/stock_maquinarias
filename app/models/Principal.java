package models;

import java.io.InputStream;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity(name="principal")
public class Principal extends Model {
	
	public String dni;
	
	public String ciudadano;
	
	public String direccionCiudadano;
	
	public String telefono;
	
	public String ruc;
	
	public String empresa;
	
	public String direccionEmpresa;
	
	public String fechaInicio;
	
	public String fechaFin;
	
	public String stockMunicipalidad;
	
	public String costoTotal;
	
	public String municipalidad;
	
	public Date fechaCreacion;
	
}
