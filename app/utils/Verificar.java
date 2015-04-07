package utils;

public class Verificar {
	
	public boolean esOk = true;	
	public String mensaje = "";
	
	public Verificar(boolean esOk, String mensaje) {
		super();
		this.esOk = esOk;
		this.mensaje = mensaje;
	}
	
	public boolean isEsOk() {
		return esOk;
	}
	
	public void setEsOk(boolean esOk) {
		this.esOk = esOk;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
