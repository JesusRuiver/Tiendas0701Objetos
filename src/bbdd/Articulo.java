package bbdd;

import java.io.Serializable;

public class Articulo implements Serializable {

	private String nombreArticulo;
	private String codFabricante; // Inicialmente lo declaro como iny pero al
									// gace la consulta para que segun el
									// CodFabricamen me de el nombreFabricante lo he tenido que cambiar a String
	private int peso;
	private String categoria;
	private int precioVenta;
	private int precioCosto;
	private int existencias;
	
	private int totalUnidadesVendidas;

	public Articulo() {

	}

	public Articulo(String nombreArticulo, String codFabricante, int peso, String categoria, int precioVenta,
			int precioCosto, int existencias) {

		this.nombreArticulo = nombreArticulo;
		this.codFabricante = codFabricante;
		this.peso = peso;
		this.categoria = categoria;
		this.precioVenta = precioVenta;
		this.precioCosto = precioCosto;
		this.existencias = existencias;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public String getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(String codFabricante) {
		this.codFabricante = codFabricante;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(int precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(int precioCosto) {
		this.precioCosto = precioCosto;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	
	public int getTotalUnidadesVendidas() {
		return totalUnidadesVendidas;
	}

	public void setTotalUnidadesVendidas(int totalUnidadesVendidas) {
		this.totalUnidadesVendidas = totalUnidadesVendidas;
	}

	@Override
	public String toString() {
		return "Articulo: " + getNombreArticulo() + " " + " Fabricante: " + getCodFabricante();
	}

}
