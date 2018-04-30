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

	public ArrayList<Articulo> rellenaComboBoxArticulos() {

		ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

		PreparedStatement consultaPreparadaArticulosFabricates; // Todavia no lo
																// utilizamos
																// porque
																// faltaria
																// introducir en
																// la sentencia
																// el nif de la
																// tiendas

		String consulta = "Select articulos.articulo, fabricantes.nombre from articulos, fabricantes where articulos.cod_fabricante = fabricantes.cod_fabricante";

		try {
			resultado = this.sentencia.executeQuery(consulta);
			System.out.println("Correcto");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error");
		}

		try {
			while (resultado.next()) {

				Articulo articulo = new Articulo();

				articulo.setNombreArticulo(resultado.getString(1));
				articulo.setCodFabricante(resultado.getString(2));

				listaArticulos.add(articulo);
			}
			resultado.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaArticulos;

	}

	public ArrayList<Venta> rellenaTablaVentas(String nif) {

		ArrayList<Venta> datos = new ArrayList<Venta>();

		PreparedStatement enviaConsultaArticulosVentas;
		String consultaPreparadaArticulosVentas = "select v.nif, v.articulo, f.nombre, v.peso, v.categoria, v.fecha_venta, "
				+ "v.unidades_vendidas as 'Unidades Vendidas', a.precio_venta, (v.unidades_vendidas * a.precio_venta) as 'Total Uds. Ventas' "
				+ "from ventas v inner join articulos a on v.articulo = a.articulo and v.categoria = a.categoria, fabricantes f "
				+ "where nif=? and v.cod_fabricante = f.cod_fabricante";

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
				filasVenta.setPrecioVenta(resultado.getInt(8));

				filasVenta.setTotalUnidadesVendidas(resultado.getInt("Total Uds. Ventas"));

				datos.add(filasVenta);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;

	}

	public ArrayList<Pedido> rellenaTablaPedidos(String nif) {

		ArrayList<Pedido> datos = new ArrayList<Pedido>();

		PreparedStatement enviaConsultaArticulosPedidos;

		String consultaPreparadaArticulosPedido = "select p.nif, p.articulo, f.nombre, p.peso, p.categoria, p.fecha_pedido, "
				+ "p.unidades_pedidas, a.precio_costo, (p.unidades_pedidas * a.precio_costo) as 'Total Uds. Pedidos' "
				+ "from pedidos p inner join articulos a on p.articulo = a.articulo and p.categoria = a.categoria, fabricantes f "
				+ "where nif=? and p.cod_fabricante = f.cod_fabricante";

		try {

			enviaConsultaArticulosPedidos = conexion.prepareStatement(consultaPreparadaArticulosPedido);

			enviaConsultaArticulosPedidos.setString(1, nif);

			resultado = enviaConsultaArticulosPedidos.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultado.next()) {

				Pedido filasPedido = new Pedido();

				filasPedido.setNif(resultado.getString(1));
				filasPedido.setNombreArticulo(resultado.getString(2));
				filasPedido.setCodFabricante(resultado.getString(3));
				filasPedido.setPeso(resultado.getInt(4));
				filasPedido.setCategoria(resultado.getString(5));
				filasPedido.setFechaPedido(resultado.getString(6));
				filasPedido.setUnidadesPedidas(resultado.getInt(7));
				filasPedido.setPrecioCosto(resultado.getInt(8));

				filasPedido.setTotalUnidadesPedidas(resultado.getInt("Total Uds. Pedidos"));

				datos.add(filasPedido);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datos;
	}

	public String sumaPrecioCosto(String nif) {

		String totalPedidos = null;

		PreparedStatement enviaConsultaSumaPedidos;

		String consultaPreparadaSumaPedidos = "select sum(p.unidades_pedidas * a.precio_costo) as 'Total Uds. Pedidas' "
				+ "from pedidos p inner join articulos a on p.articulo = a.articulo and p.categoria = a.categoria, fabricantes f "
				+ "where nif=? and p.cod_fabricante = f.cod_fabricante;";

		try {

			enviaConsultaSumaPedidos = conexion.prepareStatement(consultaPreparadaSumaPedidos);
			enviaConsultaSumaPedidos.setString(1, nif);

			resultado = enviaConsultaSumaPedidos.executeQuery();

			if (resultado.next()) {

				totalPedidos = resultado.getString("Total Uds. Pedidas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totalPedidos;
	}

	public String sumaPrecioVenta(String nif) {

		String totalVentas = "";

		PreparedStatement enviaConsultaSumaVentas;

		String consultaPreparadaSumaVentas = "select sum(v.unidades_vendidas * a.precio_venta) as 'Total Uds. Vendidas' "
				+ "from ventas v inner join articulos a on v.articulo = a.articulo and v.categoria = a.categoria, fabricantes f "
				+ "where nif=? and v.cod_fabricante = f.cod_fabricante";

		try {
			enviaConsultaSumaVentas = conexion.prepareStatement(consultaPreparadaSumaVentas);

			enviaConsultaSumaVentas.setString(1, nif);

			resultado = enviaConsultaSumaVentas.executeQuery();

			if (resultado.next()) {
				totalVentas = resultado.getString("Total Uds. Vendidas");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(totalVentas);

		return totalVentas;
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
