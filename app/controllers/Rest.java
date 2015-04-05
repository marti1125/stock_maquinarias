package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import dtos.ObtenerPrecioTipoMaquinaria;
import dtos.ObtenerCodigoNombreMaquinariaDto;
import dtos.TipoMaquinariaDto;

import models.Municipalidad;
import models.StockMunicipalidad;
import models.TipoMaquinaria;

import play.mvc.Controller;
import play.mvc.Http.Header;

public class Rest extends Controller {
	
	public static void tiposDeMaquinarias() throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
		List<TipoMaquinaria> tiposDeMaquinarias = TipoMaquinaria.findAll();
		
		List<TipoMaquinariaDto> lista = new ArrayList<TipoMaquinariaDto>();
		
		for(TipoMaquinaria tipoMaquinaria : tiposDeMaquinarias){
			lista.add(new TipoMaquinariaDto(String.valueOf(tipoMaquinaria.id),tipoMaquinaria.nombre));
		}
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(lista));
	}
	
	public static void municipalidades() throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
		List<Municipalidad> municipalidades = Municipalidad.findAll();
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(municipalidades));
		
	}
	
	public static void codigoPorMaquinaria() throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
		List<Object[]> stocksDeMunicipalidades = StockMunicipalidad.find("select sm.codigo, tm.nombre from stock_municipalidad sm " +
			" inner join sm.tipoMaquinaria tm").fetch();
		
		List<ObtenerCodigoNombreMaquinariaDto> listCodigoPorMaquinaria = new ArrayList<ObtenerCodigoNombreMaquinariaDto>();
		
		for(Object[] sm : stocksDeMunicipalidades){
			listCodigoPorMaquinaria.add(new ObtenerCodigoNombreMaquinariaDto((String)sm[0],(String)sm[1]));
		}
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(listCodigoPorMaquinaria));
		
	}
	
	public static void obtenerPrecioPorHora(String codigo) throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
		Object precio = StockMunicipalidad.find("select a.p_hora from stock_municipalidad sm inner join sm.tipoMaquinaria tm " +
        		" inner join tm.alquileres a where sm.codigo = '" + codigo + "'").first();
		
		ObtenerPrecioTipoMaquinaria obtenerPrecio = new ObtenerPrecioTipoMaquinaria((Double)precio);
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(obtenerPrecio));
		
	}
	
	public static void obtenerFechas(String codigo) throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
	}
	
	
}
