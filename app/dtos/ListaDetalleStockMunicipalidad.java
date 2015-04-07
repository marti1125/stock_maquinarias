package dtos;

public class ListaDetalleStockMunicipalidad {
	
	public String codigoStockMunicipalidad;
	public String nombreTipoMaquinaria;
	public long idDetalleStockMunicipalidad;
	public String fechaInicio;
	public String fechaFin;
	public double costoTotal;
	
	public ListaDetalleStockMunicipalidad(String codigoStockMunicipalidad,
			String nombreTipoMaquinaria, long idDetalleStockMunicipalidad,
			String fechaInicio, String fechaFin, double costoTotal) {
		super();
		this.codigoStockMunicipalidad = codigoStockMunicipalidad;
		this.nombreTipoMaquinaria = nombreTipoMaquinaria;
		this.idDetalleStockMunicipalidad = idDetalleStockMunicipalidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.costoTotal = costoTotal;
	}

}
