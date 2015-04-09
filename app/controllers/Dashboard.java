package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dtos.PrincipalDto;

import models.Principal;
import models.StockMunicipalidad;
import play.mvc.Controller;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Dashboard extends Controller {
	
	public static void index() {
		
		List<Principal> principales = Principal.findAll();
		
		List<PrincipalDto> datos = new ArrayList<PrincipalDto>();
		
		for(Principal p : principales){
			PrincipalDto principal = new PrincipalDto();
			principal.dni = p.dni;
			principal.ciudadano = p.ciudadano;
			principal.direccionCiudadano = p.direccionCiudadano;
			principal.telefono = p.telefono;
			principal.ruc = p.ruc;	
			principal.empresa = p.empresa;	  	
			principal.direccionEmpresa = p.direccionEmpresa;   	
			principal.fechaInicio = p.fechaInicio; 
			principal.fechaFin = p.fechaFin;
			StockMunicipalidad nombre = new StockMunicipalidad().findById(p.stockMunicipalidad);
			principal.stockMunicipalidad = 	nombre.tipoMaquinaria.nombre;     	
			principal.costoTotal = p.costoTotal;
			principal.municipalidad = p.municipalidad;
			principal.fechaCreacion = p.fechaCreacion;
			datos.add(principal);
		}
		
		render(datos);
	}
	
}
