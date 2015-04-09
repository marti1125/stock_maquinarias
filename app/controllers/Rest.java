package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import aux_models.Propiedad;
import aux_models.Evento;

import dtos.ListaDetalleStockMunicipalidad;
import dtos.ObtenerPrecioTipoMaquinaria;
import dtos.ObtenerCodigoNombreMaquinariaDto;
import dtos.TipoMaquinariaDto;

import models.DetalleStockMunicipalidad;
import models.Movimiento;
import models.Municipalidad;
import models.StockMunicipalidad;
import models.TipoMaquinaria;

import play.mvc.Controller;
import play.mvc.Http.Header;
import utils.Verificar;

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
		
		ObtenerPrecioTipoMaquinaria obtenerPrecio = new ObtenerPrecioTipoMaquinaria();
		
		if(precio != null){
			obtenerPrecio = new ObtenerPrecioTipoMaquinaria((Double)precio);
		}
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(obtenerPrecio));
		
	}
	
	public static void obtenerFechas(String codigo) throws JsonGenerationException, JsonMappingException, IOException {
		
		response.headers.put("Access-Control-Allow-Origin", new Header("Access-Control-Allow-Origin", "*"));
		
		List<Object[]> fechas = Movimiento.find("select m.fechaInicio, m.fechaFin from movimiento m where m.stockMunicipalidad = '" + codigo + "'").fetch();
		
		Evento eventos = new Evento();
		List<Propiedad> lp = new ArrayList<Propiedad>();
		
		if(fechas != null){
			for(Object[] fecha : fechas){
	        	
	        	Propiedad p = new Propiedad("Reservado", String.valueOf(fecha[0]).replace(" 00:00:00.0", ""), 
	        			String.valueOf(fecha[1]).replace(" 00:00:00.0", ""));
	        	lp.add(p);
	        	
	        }
		}
		
		eventos = new Evento(lp, "#26A2E0", "white");
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(eventos));
		
	}
	
	public static void nuevoPrincipal(String dni, String ciudadano, String direccionCiudadano, String ruc,
            						  String empresa, String direccionEmpresa, double costoTotal, long municipalidad) throws JsonGenerationException, 
            						  JsonMappingException, IOException {
		
		Municipalidad municipalidadEncontrada = Municipalidad.findById(municipalidad);
		
		models.Principal principal = new models.Principal();
		principal.dni = dni;
		principal.ciudadano = ciudadano;
		principal.direccionCiudadano = direccionCiudadano;
		principal.ruc = ruc;
		principal.empresa = empresa;
		principal.direccionEmpresa = direccionEmpresa;
		principal.costoTotal = costoTotal;
		principal.municipalidad = municipalidadEncontrada;
		principal.save();
		
		Verificar verificar = new Verificar(true, "OK!");
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(verificar));		
		
	}
	
	public static void listaDetalleStockMunicipalidad() throws JsonGenerationException, JsonMappingException, IOException {
		
		List<Object[]> listas = DetalleStockMunicipalidad.find("select sm.codigo, tm.nombre, dsm.id, dsm.fechaInicio, dsm.fechaFin, dsm.costoTotal " +
    			" from detalle_stockmunicipalidad dsm inner join dsm.stockMunicipalidad sm inner join sm.tipoMaquinaria tm ").fetch();
		
		List<ListaDetalleStockMunicipalidad> detalles = new ArrayList<ListaDetalleStockMunicipalidad>();
		
		for(Object[] lista : listas) {
	        	
        	String codigoStockMunicipalidad = (String)lista[0];
        	String nombreTipoMaquinaria = String.valueOf(lista[1]);
        	long idDetalleStockMunicipalidad = (Long)lista[2];
        	String fechaInicio = String.valueOf(lista[3]).replace(" 00:00:00.0", "");
        	String fechaFin = String.valueOf(lista[4]).replace(" 00:00:00.0", "");
        	double costoTotal = (Double)lista[5];
        	
            detalles.add(new ListaDetalleStockMunicipalidad(codigoStockMunicipalidad, nombreTipoMaquinaria,
        			idDetalleStockMunicipalidad, fechaInicio, fechaFin,	costoTotal));
            
	    }
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(detalles));
		
	}
	
	public static void nuevoDetalleStockMunicipalidad(String stockMunicipalidad, String fechaInicio, String fechaFin, double costoTotal) 
			                                                          throws JsonGenerationException, JsonMappingException, IOException, 
			                                                          ParseException {
		
		StockMunicipalidad stockMunicipalidadEncontrado = StockMunicipalidad.findById(stockMunicipalidad);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        Date inicio = formatter.parse(fechaInicio);
        Date fin = formatter.parse(fechaFin);
        
        DetalleStockMunicipalidad detalle = new DetalleStockMunicipalidad();
        detalle.stockMunicipalidad = stockMunicipalidadEncontrado;
        detalle.fechaInicio = inicio;
        detalle.fechaInicio = fin;
        detalle.costoTotal = costoTotal;
        detalle.save();
		
		Verificar verificar = new Verificar(true, "OK!");
		
		org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();		
		renderJSON(mapper.writeValueAsString(verificar));
		
	}
	
}
