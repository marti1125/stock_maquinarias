package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel;


@Entity
public class Principal extends GenericModel {
	
	@Id
	public long id;
	
	public String dni;
	
	public String ciudadano;
	
	public String direccionCiudadano;
	
	public String ruc;
	
	public String empresa;
	
	public String direccionEmpresa;
	
	public double costoTotal;
	
	@ManyToOne
	@JoinColumn(name="municipalidad")
	public Municipalidad municipalidad;

}
