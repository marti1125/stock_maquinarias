package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.GenericModel;

@Entity(name="alquiler")
public class Alquiler extends GenericModel {
	
	@Id
	public long id;
	
	@ManyToOne
	@JoinColumn(name="tipo_maquinaria")
	public TipoMaquinaria tipo_maquinaria;
	
	public double p_hora;
	
}
