package com.erp.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormEmployee extends JFrame {
	private JPanel pnlContenedor;
	private JPanel pnlCentro;
	private JButton btnGuardar;
	private JLabel lblTitulo;
	private JLabel lblNombre;
	private JLabel lblAge;
	private JTextField txtNombre;
	private Employee entidad;
	private JTextField txtAge;
	private int id;
	private ListEmployees lst;
	private DAOEmployeeImpl dao;

	public FormEmployee(String frameTitle, int id, ListEmployees lst) {
		this.dao = new DAOEmployeeImpl();
		this.id = id;
		this.lst = lst;
		if(id == -1)
			this.entidad = new Employee();
		else
			this.entidad = dao.findById(id); 
		// Establecer el titulo de la ventana
		this.setTitle(frameTitle);
		// Establecer la dimension de la ventana (ancho, alto)
		this.setSize(400, 250);
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
		pnlContenedor.setLayout(new BorderLayout());
		if (this.id == -1)
			lblTitulo = new JLabel("Add Employee");
		else
			lblTitulo = new JLabel("Edit Employee");
		lblTitulo.setFont(new Font("Serif", Font.BOLD, 20));
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		pnlContenedor.add(lblTitulo, BorderLayout.PAGE_START);
		pnlContenedor.add(getPanelCentro(), BorderLayout.CENTER);
		return pnlContenedor;
	}

	private JPanel getPanelCentro() {
		pnlCentro = new JPanel();
		pnlCentro.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		lblNombre = new JLabel("Name:");
		lblNombre.setHorizontalAlignment(JLabel.RIGHT);
		gbc.gridx = 0; // número columna
		gbc.gridy = 0; // número fila
		gbc.gridwidth = 1; // numero de columnas de ancho
		gbc.gridheight = 1; // numero de filas de ancho
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL; // rellenar la celda en ambos
													// sentidos (horizontal y
													// vertical)
		gbc.insets = new Insets(3, 3, 3, 3); // definir el relleno exterior
		pnlCentro.add(lblNombre, gbc); // agregar el label al panel contenedor

		txtNombre = new JTextField(entidad.getName());
		gbc.gridx = 1; // número columna
		gbc.gridy = 0; // número fila
		gbc.weightx = 0.9;
		pnlCentro.add(txtNombre, gbc); // agregar el textField al panel
										// contenedor

		lblAge = new JLabel("Age:");
		lblAge.setHorizontalAlignment(JLabel.RIGHT);
		gbc.gridx = 0; // número columna
		gbc.gridy = 1; // número fila
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		pnlCentro.add(lblAge, gbc); // agregar el textField al panel contenedor

		txtAge = new JTextField(Integer.toString(entidad.getAge()));
		gbc.gridx = 1; // número columna
		gbc.gridy = 1; // número fila
		gbc.weightx = 0.9;
		pnlCentro.add(txtAge, gbc); // agregar el textField al panel contenedor

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNombre.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the name");
					txtNombre.requestFocusInWindow();
					return;
				}
				entidad.setName(txtNombre.getText());

				if (txtAge.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the age");
					txtAge.requestFocusInWindow();
					return;
				}
				entidad.setAge(Integer.parseInt(txtAge.getText()));

				if (id == -1)
					dao.add(entidad);
				else {
					entidad.setId(id);
					dao.edit(entidad);
				}

				FormEmployee.this.lst.fillTable("");
				closeWin();
			}
		});

		gbc.gridx = 0; // número columna
		gbc.gridy = 4; // número fila
		gbc.gridwidth = 2; // numero de columnas de ancho
		gbc.fill = GridBagConstraints.NONE; // rellenar la celda en ambos
											// sentidos (horizontal y vertical)
		pnlCentro.add(btnGuardar, gbc); // agregar el textField al panel
										// contenedor

		return pnlCentro;
	}

	private void closeWin() {
		this.setVisible(false);
		this.dispose();
	}
}
