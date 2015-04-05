package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel;

@Entity
public class DetalleStockMunicipalidad extends GenericModel {
	
	@Id
	public long id;
	
	@ManyToOne
	public StockMunicipalidad stockMunicipalidad;
	
	public Date fechaInicio;
	
	public Date fechaFin;
	
	public double costoTotal;
	
}
