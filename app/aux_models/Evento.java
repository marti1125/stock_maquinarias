package aux_models;

import java.util.List;

import aux_models.Propiedad;

public class Evento {
	
	public List<Propiedad> eventos;
	public String color;
	public String textColor;
	
	public Evento(){
		
	}
	
	public Evento(List<Propiedad> eventos, String color, String textColor) {
		super();
		this.eventos = eventos;
		this.color = color;
		this.textColor = textColor;
	}
	
}
