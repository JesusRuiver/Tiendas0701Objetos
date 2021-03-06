package bbdd;

import java.util.Arrays;

public class Fabricante {

	private int codFabricante;
	private String nombre;
	private String pais;
	private Articulo[] articulos;

	public Fabricante() {

	}

	public Fabricante(int codFabricante, String nombre, String pais) {

		this.codFabricante = codFabricante;
		this.nombre = nombre;
		this.pais = pais;
	}

	public int getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(int codFabricante) {
		this.codFabricante = codFabricante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Articulo[] getArticulos() {
		return articulos;
	}

	public void setArticulos(Articulo[] articulos) {
		this.articulos = articulos;
	}

	@Override
	public String toString() {
		return "Fabricante [codFabricante=" + codFabricante + ", nombre=" + nombre + ", pais=" + pais + "]";
	}

}
