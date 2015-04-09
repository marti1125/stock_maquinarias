package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel;

@Entity(name="movimiento")
public class Movimiento extends GenericModel {
	
	@Id
	public long id;
	
	@ManyToOne
	@JoinColumn(name="stock_municipalidad")
	public StockMunicipalidad stockMunicipalidad;
	
	public Date fechaInicio;
	
	public Date fechaFin;
	
	public String estado;
	
}
