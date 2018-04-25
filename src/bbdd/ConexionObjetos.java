package bbdd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConexionObjetos {

	private Connection conexion;
	private String usuario;
	private String contraseña;
	private String baseDatos;
	private String servidor;
	private String conectorJDBC;

	private ResultSet resultado;
	private Statement sentencia;

	public ConexionObjetos() {

	}

	public ConexionObjetos(Connection conexion, String usuario, String contraseña, String baseDatos, String servidor,
			String conectorJDBC) {
		this.conexion = conexion;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.baseDatos = baseDatos;
		this.servidor = servidor;
		this.conectorJDBC = conectorJDBC;
	}

	public void conectar() {

		this.conectorJDBC = "com.mysql.jdbc.Driver";
		this.servidor = "jdbc:mysql://localhost/";
		this.baseDatos = "tiendas";
		this.usuario = "tiendas";
		this.contraseña = "tiendas";

		try {

			Class.forName(getConectorJDBC());

			this.conexion = DriverManager.getConnection(getServidor() + getBaseDatos(), getUsuario(), getContraseña());

			this.sentencia = conexion.createStatement();

			System.out.println("Conectado");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error... No Conectado");
			e.printStackTrace();
		}

	}

	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Tienda> rellenaComboBoxTiendas() {

		ArrayList<Tienda> lista = new ArrayList<Tienda>();
		String consulta = "SELECT * FROM tiendas";

		try {
			resultado = this.sentencia.executeQuery(consulta);

			System.out.println("Correcto");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error");
		}

		try {
			while (resultado.next()) {

				Tienda tiendas = new Tienda();

				tiendas.setNombre(resultado.getString(2));
				tiendas.setNif(resultado.getString(1));

				lista.add(tiendas);

			}
			resultado.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;

	}

	public ArrayList<String> rellenaComboBoxArticulos() {

		ArrayList<String> lista = new ArrayList<String>();
		String consulta = "SELECT * FROM articulos";
		try {
			resultado = this.sentencia.executeQuery(consulta);
			System.out.println("Correcto");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error");
		}

		try {
			while (resultado.next()) {

				lista.add("ARTICULO: " + resultado.getString(1) + " FABRICANTE: " + resultado.getString(2));
			}
			resultado.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;

	}

	public ArrayList<Venta> rellenaTablaVentas(String nif) {

		ArrayList<Venta> datos = new ArrayList<Venta>();

		PreparedStatement enviaConsultaArticulosVentas;
		String consultaPreparadaArticulosVentas = "Select nif, ventas.articulo, fabricantes.nombre, ventas.peso, ventas.categoria, ventas.fecha_venta, ventas.unidades_vendidas  from ventas, fabricantes where nif =? and ventas.cod_fabricante = fabricantes.cod_fabricante";
		try {

			enviaConsultaArticulosVentas = conexion.prepareStatement(consultaPreparadaArticulosVentas);
			enviaConsultaArticulosVentas.setString(1, nif);

			resultado = enviaConsultaArticulosVentas.executeQuery();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultado.next()) {

				Venta filasVenta = new Venta();

				filasVenta.setNif(resultado.getString(1));
				filasVenta.setArticulo(resultado.getString(2));
				filasVenta.setCodFabricante(resultado.getString(3));
				filasVenta.setPeso(resultado.getInt(4));
				filasVenta.setCategoria(resultado.getString(5));
				filasVenta.setFechaVenta(resultado.getString(6));
				filasVenta.setUnidadesVendidas(resultado.getInt(7));

				datos.add(filasVenta);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;

	}

	public String sumaPrecioCosto() {

		String consulta = "Select SUM(precio_costo) as sumaPrecioCosto from articulos";
		String total = "";
		try {
			resultado = sentencia.executeQuery(consulta);

			if (resultado.next()) {
				total = resultado.getString("sumaPrecioCosto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return total;
	}

	public String sumaPrecioVenta() {

		String consulta = "Select SUM(precio_venta) as sumaVentas from articulos";
		String total = "";
		try {

			resultado = sentencia.executeQuery(consulta);

			if (resultado.next()) {
				total = resultado.getString("sumaVentas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public ObjectOutputStream exportarFicheroBinario() throws IOException {

		FileOutputStream fichero = new FileOutputStream("FicheroBinaro.dat");

		ObjectOutputStream fileout = new ObjectOutputStream(fichero);

		return fileout;

	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getConectorJDBC() {
		return conectorJDBC;
	}

	public void setConectorJDBC(String conectorJDBC) {
		this.conectorJDBC = conectorJDBC;
	}

}
