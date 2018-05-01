package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import bbdd.ConexionObjetos;
import bbdd.Pedido;
import bbdd.Tienda;
import bbdd.Venta;

public class Ejercicio1Objetos extends JFrame {

	private JPanel contentPane;
	private JTable tablaVentas;
	private JTable tablaPedidos;
	private JLabel lbResultadoTotal = new JLabel("");
	private JScrollPane scrollPaneVentas;
	private JScrollPane scrollPanePedidos;
	private JComboBox<Tienda> cboxTiendas;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton rbtnVentas = new JRadioButton("Ventas");
	private JRadioButton rbtnPedidos = new JRadioButton("Pedidos");

	private ConexionObjetos miConexion = new ConexionObjetos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio1Objetos frame = new Ejercicio1Objetos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame. //Constructor de la Ventana
	 */
	public Ejercicio1Objetos() {

		miConexion.conectar();

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cboxTiendas = new JComboBox<Tienda>();
		cboxTiendas.setBounds(33, 22, 262, 26);
		contentPane.add(cboxTiendas);

		buttonGroup.add(rbtnVentas);

		rbtnVentas.setBounds(65, 66, 77, 23);
		contentPane.add(rbtnVentas);

		buttonGroup.add(rbtnPedidos);

		rbtnPedidos.setBounds(65, 92, 77, 23);
		contentPane.add(rbtnPedidos);

		JLabel lbTotal = new JLabel("Total:");
		lbTotal.setFont(new Font("Arial", Font.BOLD, 15));
		lbTotal.setBounds(330, 528, 46, 14);
		contentPane.add(lbTotal);
		lbResultadoTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lbResultadoTotal.setBounds(386, 521, 214, 26);
		contentPane.add(lbResultadoTotal);

		scrollPaneVentas = new JScrollPane();
		scrollPaneVentas.setBounds(33, 127, 801, 374);
		contentPane.add(scrollPaneVentas);

		scrollPanePedidos = new JScrollPane();
		scrollPanePedidos.setBounds(33, 127, 801, 374);
		contentPane.add(scrollPanePedidos);

		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/

		rellenaComboBox(cboxTiendas);

		rbtnVentas.setSelected(true);

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNifComboTiendas();

				String resultadoTotalVentas = miConexion.sumaPrecioVenta(nif);

				construirTablaVentas(nif);

				lbResultadoTotal.setText(resultadoTotalVentas + " € Ingresos Ventas");

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNifComboTiendas();

				String resultadoTotalPedidos = miConexion.sumaPrecioCosto(nif);

				construirTablaPedidos(nif);

				lbResultadoTotal.setText(resultadoTotalPedidos + "€ Coste Pedidos");

			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNifComboTiendas();

				String resultadoTotalVentas = miConexion.sumaPrecioVenta(nif);

				String resultadoTotalPedidos = miConexion.sumaPrecioCosto(nif);

				if (rbtnVentas.isSelected() == true) {

					construirTablaVentas(nif);

					lbResultadoTotal.setText(resultadoTotalVentas + " € Ingresos Ventas");

				} else {

					construirTablaPedidos(nif);

					lbResultadoTotal.setText(resultadoTotalPedidos + "€ Coste Pedidos");

					System.out.println("Esta seleccionando pedidos");
				}

			}

		});

	}

	/*-------------------------------------METODOS-----------------------------------*/

	private String seleccionaNifComboTiendas() {

		String nif;

		String tiendaYnif;

		tiendaYnif = cboxTiendas.getSelectedItem().toString().trim();

		String parteNif[] = tiendaYnif.split(": ");

		nif = parteNif[2];

		return nif;

	}

	private void construirTablaVentas(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES VENDIDAS", "PRECIO VENTA", "TOTAL VENTAS" };
		String informacionTablaVentas[][] = obtenerDatosVentas(nif);

		tablaVentas = new JTable(informacionTablaVentas, titulosColumnas);
		scrollPaneVentas.setViewportView(tablaVentas);

	}

	private String[][] obtenerDatosVentas(String nif) {

		ArrayList<Venta> datos = new ArrayList<Venta>();

		datos = miConexion.rellenaTablaVentas(nif);

		String matrizInfo[][] = new String[datos.size()][9];// matriz [fila]
															// [columna]

		for (int i = 0; i < datos.size(); i++) {

			matrizInfo[i][0] = datos.get(i).getNif();
			matrizInfo[i][1] = datos.get(i).getArticulo();
			matrizInfo[i][2] = datos.get(i).getCodFabricante();
			matrizInfo[i][3] = datos.get(i).getPeso() + "";
			matrizInfo[i][4] = datos.get(i).getCategoria();
			matrizInfo[i][5] = datos.get(i).getFechaVenta();
			matrizInfo[i][6] = datos.get(i).getUnidadesVendidas() + "";
			matrizInfo[i][7] = datos.get(i).getPrecioVenta() + "";
			matrizInfo[i][8] = datos.get(i).getTotalUnidadesVendidas() + "";
		}

		return matrizInfo;
	}

	private void construirTablaPedidos(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA PEDIDO",
				"UNIDADES PEDIDAS", "PRECIO COSTO", "TOTAL PEDIDOS" };
		String informacionTablaPedidos[][] = obtenerDatosPedidos(nif);

		tablaPedidos = new JTable(informacionTablaPedidos, titulosColumnas);
		scrollPanePedidos.setViewportView(tablaPedidos);

	}

	private String[][] obtenerDatosPedidos(String nif) {

		ArrayList<Pedido> datos = new ArrayList<Pedido>();

		datos = miConexion.rellenaTablaPedidos(nif);

		String matrinzInfo[][] = new String[datos.size()][9];

		for (int i = 0; i < datos.size(); i++) {

			matrinzInfo[i][0] = datos.get(i).getNif();
			matrinzInfo[i][1] = datos.get(i).getNombreArticulo();
			matrinzInfo[i][2] = datos.get(i).getCodFabricante();
			matrinzInfo[i][3] = datos.get(i).getPeso() + "";
			matrinzInfo[i][4] = datos.get(i).getCategoria();
			matrinzInfo[i][5] = datos.get(i).getFechaPedido();
			matrinzInfo[i][6] = datos.get(i).getUnidadesPedidas() + "";
			matrinzInfo[i][7] = datos.get(i).getPrecioCosto() + "";
			matrinzInfo[i][8] = datos.get(i).getTotalUnidadesPedidas() + "";

		}

		return matrinzInfo;

	}

	public void rellenaComboBox(JComboBox<Tienda> cboxTiendas) {
		ArrayList<Tienda> lista = new ArrayList<Tienda>();

		lista = miConexion.rellenaComboBoxTiendas();

		for (int i = 0; i < lista.size(); i++) {

			cboxTiendas.addItem(lista.get(i));

		}
	}

}
