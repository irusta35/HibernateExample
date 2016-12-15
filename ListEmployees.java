package com.erp.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

//import com.lessons.frontend.Program;

public class ListEmployees extends JFrame {
	private JPanel pnlContenedor;
	private JPanel pnlInferior;
	private JTable tblItems;
	private DefaultTableModel dataModel;
	private JLabel lblTitulo;
	private ButtonColumn buttonColumn;
	private JButton btnAlta;
	private ListEmployees me;
	private DAOEmployeeImpl dao;

	public ListEmployees() {
		this.dao = new DAOEmployeeImpl();
		this.me = this;
		// Establecer el titulo de la ventana
		this.setTitle("Employees");
		// Establecer la dimension de la ventana (ancho, alto)
		this.setSize(750, 400);
		// Establecer NO dimensionable la ventana
		this.setResizable(false);
		// Ubicar la ventana en el centro de la pantalla
		this.setLocationRelativeTo(null);
		// Agregar el panel al JFrame
		this.getContentPane().add(this.getPanelContenedor());
		// Mostrar la ventana
		this.setVisible(true);
	}

	private JPanel getPanelContenedor() {
		pnlContenedor = new JPanel();
		pnlInferior = new JPanel();
		pnlContenedor.setLayout(new BorderLayout());

		lblTitulo = new JLabel("List Employees");
		lblTitulo.setFont(new Font("Serif", Font.BOLD, 20));
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);

		pnlContenedor.add(lblTitulo, BorderLayout.PAGE_START);
		pnlContenedor.add(getJTable(), BorderLayout.CENTER);
		pnlContenedor.add(getPanelInferior(), BorderLayout.PAGE_END);
		return pnlContenedor;
	}

	private JPanel getPanelInferior() {
		pnlInferior.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		btnAlta = new JButton("Add");
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new FormCliente("Alta Cliente", -1, ListEmployees.this.me);
				new FormEmployee("Add Employee", -1, me);
			}
		});
		gbc.gridx = 0; // número columna
		gbc.gridy = 0; // número fila
		gbc.gridwidth = 1; // numero de columnas de ancho
		gbc.gridheight = 1; // numero de filas de ancho
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL; // rellenar la celda en vertical
		gbc.insets = new Insets(3, 3, 3, 3); // definir el relleno exterior
		pnlInferior.add(btnAlta, gbc); // agregar el textField al panel
										// contenedor
		return pnlInferior;
	}

	private JScrollPane getJTable() {
		tblItems = new JTable();
		fillTable("");
		JScrollPane scrollPane = new JScrollPane(tblItems);
		return scrollPane;
	}

	public void fillTable(String txt) {
		Vector<String> aux;
		String[] cabecera = { "Id", "Name", "Age", "", "" };

		dataModel = new DefaultTableModel();
		dataModel.setColumnCount(5);
		dataModel.setColumnIdentifiers(cabecera);
		tblItems.setModel(dataModel);
		tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Collection<Cliente> items = dao.getClientes();
		Collection<Employee> items = getItems();
		for (Employee item : items) {
			aux = new Vector<String>();
			aux.add(Integer.toString(item.getId()));
			aux.add(item.getName());
			aux.add(Integer.toString(item.getAge()));
			dataModel.addRow(aux);
		}

		if (tblItems == null)
			tblItems = new JTable();

		Action a = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = tblItems.getSelectedRow();
				int id = Integer.parseInt(tblItems.getValueAt(row, 0).toString());
				new FormEmployee("Edit Employee", id, me);
			}
		};
		buttonColumn = new ButtonColumn(tblItems, a, 3, "Edit");

		a = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = tblItems.getSelectedRow();
				int id = Integer.parseInt(tblItems.getValueAt(row, 0).toString());
				dao.delete(id);
				fillTable("");
			}
		};
		buttonColumn = new ButtonColumn(tblItems, a, 4, "Delete");
	}

	private List<Employee> getItems() {
		IDAOEmployee dao = new DAOEmployeeImpl();
		return dao.getAll();
	}

	private List<Employee> getItems2() {
		List<Employee> items = new ArrayList<Employee>();
		items.add(new Employee(1, "Pepe", 45));
		items.add(new Employee(2, "Juan", 28));
		items.add(new Employee(3, "Beto", 39));
		return items;
	}
}