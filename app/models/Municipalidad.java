package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class Municipalidad extends GenericModel {
	
	@Id
	public long id;
	public String nombre;
	public String ubicacion;

}
