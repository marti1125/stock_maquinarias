package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import dtos.TipoMaquinariaDto;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void maquinarias(long id){
    	
    	List<Object[]> listas = StockMunicipalidad.find("select tm.id, tm.nombre from stock_municipalidad sm inner join sm.tipoMaquinaria tm " +
        		" where sm.municipalidad = '" + id + "'").fetch();
    	
    	List<TipoMaquinariaDto> tiposDeMaquinarias = new ArrayList<TipoMaquinariaDto>();
    	
    	for(Object[] lista : listas){
    		tiposDeMaquinarias.add(new TipoMaquinariaDto(lista[0].toString(),(String)lista[1]));
    	}
    	
    	render(tiposDeMaquinarias);
    }

}