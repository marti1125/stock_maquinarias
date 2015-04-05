package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel;

@Entity(name="stock_municipalidad")
public class StockMunicipalidad extends GenericModel {
	
	@Id
	public String codigo;
	
	@ManyToOne
	@JoinColumn(name="municipalidad")
	public Municipalidad municipalidad;
	
	@ManyToOne
	@JoinColumn(name="tipo_maquinaria")
	public TipoMaquinaria tipoMaquinaria;
	
}

