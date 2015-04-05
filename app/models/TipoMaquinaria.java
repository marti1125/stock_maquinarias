package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.jpa.GenericModel;

@Entity(name="tipo_maquinaria")
public class TipoMaquinaria extends GenericModel {
	
	@Id
	public long id;
	
	public String nombre;
	
	@OneToMany(mappedBy = "tipo_maquinaria")
    public List<Alquiler> alquileres;
	
}
