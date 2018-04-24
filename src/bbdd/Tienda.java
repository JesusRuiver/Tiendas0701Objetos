package bbdd;

import java.io.Serializable;

public class Tienda implements Serializable {

	private String nif;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String provincia;
	private int codPostal;

	public Tienda() {

	}

	public Tienda(String nif, String nombre, String direccion, String poblacion, String provincia, int codPostal) {

		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codPostal = codPostal;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	@Override
	public String toString() {
		return "TIENDA: " + getNombre() + " NIF: " + getNif();
	}

	
}
