package com.cdpjosecabrera.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.cdpjosecabrera.controlador.LibrosController;
import com.cdpjosecabrera.modelo.Libros;
import com.cdpjosecabrera.utilidades.CasillaIncorrecta;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.FechaIncorrecta;
import com.cdpjosecabrera.utilidades.IsbnIncorrecto;



import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class formulario extends JFrame {

	private JPanel panel;
	
	
	private LibrosController controlador;
	int puntero;  //puntero para saber el registro q estoy mostrando
	
												
	private String[] titulos={"idLibros","titulo","autor","editorial","isbn","prestado","fechaDevolucion"};
	
	DefaultTableModel dtm;
	private JTable table;
	JPanel panel_1;
	JScrollPane scrollPane;
	
	
	private JButton botonnuevo;
	private JButton botoneditar;
	private JButton botonborrar;
	private JButton botonguardar;
	private JButton botondeshacer;
	ImageIcon imaNav;
	private JPanel panelnavegador;
	private JButton botonprimero;
	private JButton botonanterior;
	private JButton botonsiguiente;
	private JButton botonultimo;
	private JPanel panellibros;
	private JTextField cajaid;
	private JTextField cajatitulo;
	private JTextField cajaautor;
	private JTextField cajaeditorial;
	private JTextField cajaisbn;
	private JTextField cajafecha;
	JCheckBox checkprestado; JButton botonfiltrar;
	JLabel labelidlibro, labeltitulo, labelautor, labeleditorial,labelisbn,labelprestado,labelfecha;
	private JTextField cajafiltro; JComboBox comboBox;

	
	
	boolean nuevoregistro;
	
	
	public formulario() throws ClassNotFoundException, SQLException, IsbnIncorrecto, ParseException, CasillaIncorrecta {
		setTitle("BIBLIOTECA VERSION 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1191, 698);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		controlador=new LibrosController();
		puntero=0;
		controlador.getLibros();
		
		
		definirventanas();
		definireventos();
		
		cargardatos();
		mostrardatos();
			
		setVisible(true);
	}



	private void definireventos() {
		botonnuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarPanelNavegacion(false);
				habilitarPanelMantenimiento(false);
				habilitarPanelLibros(true);
				
				
				//borra contenido cajas
				cajaid.setText("");
				cajatitulo.setText("");
				cajaautor.setText("");
				cajaeditorial.setText("");
				cajaisbn.setText("");
				checkprestado.isSelected();
				cajafecha.setText("");
				
				nuevoregistro=true;
			}
		});
		
		
		botoneditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarPanelNavegacion(false);
				habilitarPanelMantenimiento(false);
				habilitarPanelLibros(true);
			}
		});
		
		botonguardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarPanelNavegacion(true);
				habilitarPanelMantenimiento(true);
				habilitarPanelLibros(true);
				
				boolean prestado=false;
				if(checkprestado.isSelected()){
					prestado=true;
				}
				if(nuevoregistro){
				//idLibros, String titulo, String autor, String editorial, String isbn, boolean prestado,Fecha fechaDevolucion		
				try {
					
					Fecha fec=new Fecha(cajafecha.getText());
					Libros lib=new Libros(Integer.parseInt(cajaid.getText()),cajatitulo.getText(),cajaautor.getText(),cajaeditorial.getText(),cajaisbn.getText(),prestado,fec);
					controlador.agregarLibro(lib);
					JOptionPane.showMessageDialog(panel,"libro agregado");
					//cargardatos();
				
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (FechaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
					JOptionPane.showMessageDialog(panel,"fecha incorrecta");
					
				} 
//				catch (ClassNotFoundException e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} catch (ParseException e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} 
				catch (CasillaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
					JOptionPane.showMessageDialog(panel,"la casilla no puede estar vacia");
				}
				}
				else{
					try {
						
						try {
							Fecha fec = new Fecha(cajafecha.getText());
							Libros lib=new Libros(Integer.parseInt(cajaid.getText()),cajatitulo.getText(),cajaautor.getText(),cajaeditorial.getText(),cajaisbn.getText(),prestado,fec);
							controlador.modificarLibro(lib);
							JOptionPane.showMessageDialog(panel,"libro editado");
						} catch (FechaIncorrecta e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						} catch (NumberFormatException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						} catch (IsbnIncorrecto e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						} catch (CasillaIncorrecta e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						
						//actualizartabla();
					} catch (SQLException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
				}
				

				
			}
		});
		
		botonborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean prestado=false;
				if(checkprestado.isSelected()){
					prestado=true;
				}
				
				int confirmado=JOptionPane.showConfirmDialog(panel,"esta seguro de borrar el registro?");
				
				if(JOptionPane.OK_OPTION==confirmado){
					
					try {
						
						Fecha fec = new Fecha(cajafecha.getText());
						Libros lib=new Libros(Integer.parseInt(cajaid.getText()),cajatitulo.getText(),cajaautor.getText(),cajaeditorial.getText(),cajaisbn.getText(),prestado,fec);
						controlador.borrarLibro(lib);
						JOptionPane.showMessageDialog(panel,"libro borrado");
		
					} catch (SQLException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					} catch (FechaIncorrecta e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					} catch (IsbnIncorrecto e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					} catch (CasillaIncorrecta e1) {
						// TODO Bloque catch generado automáticamente
						JOptionPane.showMessageDialog(panel,"la casilla no puede estar vacia");
					} 
					
				}
				else{
					JOptionPane.showMessageDialog(panel,"error");
				}
				
				try {
					puntero=0;
					mostrardatos();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				 
			}
		});
		
		botondeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//justo lo contrario del boton nuevo
				habilitarPanelNavegacion(true);
				habilitarPanelMantenimiento(true);
				habilitarPanelLibros(false);
				
				if(nuevoregistro){
					nuevoregistro=false;
				}
				
				try {
					mostrardatos();
			} catch (ClassNotFoundException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			} catch (IsbnIncorrecto e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			}
		});
		
		
		botonprimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					puntero=0;
					mostrardatos();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
		
		botonanterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(puntero>0)
						puntero--;
						mostrardatos();
					
					
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				 
			}
		});
		
		botonsiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(puntero<controlador.getLibros().size()-1)
						puntero++;
						mostrardatos();

				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} 
				catch (CasillaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				
			}
		});
		
		botonultimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					puntero=controlador.getLibros().size()-1;
					mostrardatos();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (CasillaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
		
		
		botonfiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.filtrar(cajafiltro.getText());
					cargardatos();
					mostrardatos();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (CasillaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				
			}
		});
		
		
//		cajafiltro.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				
//				
//				try {
//					controlador.filtrar(cajafiltro.getText());
//					mostrardatos();
//					cargardatos();
//					
//				} catch (ClassNotFoundException e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} catch (IsbnIncorrecto e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} catch (ParseException e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				} catch (CasillaIncorrecta e1) {
//					// TODO Bloque catch generado automáticamente
//					e1.printStackTrace();
//				}
//				
//			}
//		});
//		
//		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					controlador.filtrar2(comboBox.getSelectedItem().toString());
					mostrardatos();
					cargardatos();
					
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (CasillaIncorrecta e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
	}



	private void habilitarPanelNavegacion(boolean hab) {
		botonprimero.setEnabled(hab);
		botonanterior.setEnabled(hab);
		botonsiguiente.setEnabled(hab);
		botonultimo.setEnabled(hab);
		
	}


	private void habilitarPanelMantenimiento(boolean hab) {
		botonnuevo.setEnabled(hab);
		botoneditar.setEnabled(hab);
		botonborrar.setEnabled(hab);
		botonguardar.setEnabled(true);
		botondeshacer.setEnabled(true);
	}


	private void habilitarPanelLibros(boolean hab) {
		cajaid.setEditable(hab);
		cajatitulo.setEditable(hab);
		cajaautor.setEditable(hab);
		cajaeditorial.setEditable(hab);
		cajaisbn.setEditable(hab);
		checkprestado.setEnabled(hab);
		cajafecha.setEditable(hab);
		
	}



	private void definirventanas() {
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(552, 128, 581, 501);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		dtm=new DefaultTableModel();
		table = new JTable(dtm);
		
		
		scrollPane = new JScrollPane();
		
		
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		scrollPane.setViewportView(table);
		
		JPanel panelmantenimiento = new JPanel();
		panelmantenimiento.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)), "Mantenimiento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panelmantenimiento.setBounds(25, 272, 503, 107);
		panel.add(panelmantenimiento);
		panelmantenimiento.setLayout(null);
		
	
		imaNav=new ImageIcon("imagenes/NUEVO.png");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonnuevo = new JButton("",imaNav);
		
		botonnuevo.setBounds(10, 27, 89, 59);
		panelmantenimiento.add(botonnuevo);
		
		imaNav=new ImageIcon("imagenes/MODIFICAR.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botoneditar = new JButton("",imaNav);
		
		botoneditar.setBounds(109, 27, 89, 59);
		panelmantenimiento.add(botoneditar);
		
		imaNav=new ImageIcon("imagenes/DELETE.png");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonborrar = new JButton("",imaNav);
		
		botonborrar.setBounds(208, 27, 89, 59);
		panelmantenimiento.add(botonborrar);
		
		imaNav=new ImageIcon("imagenes/GUARDAR.png");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonguardar = new JButton("",imaNav);
		botonguardar.setEnabled(false);
		
		botonguardar.setBounds(307, 27, 89, 59);
		panelmantenimiento.add(botonguardar);
		
		imaNav=new ImageIcon("imagenes/DESHACER.png");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botondeshacer = new JButton("",imaNav);
		botondeshacer.setEnabled(false);
		
		botondeshacer.setBounds(404, 27, 89, 59);
		panelmantenimiento.add(botondeshacer);
		
		panelnavegador = new JPanel();
		panelnavegador.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)), "Panel navegacion", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		panelnavegador.setBounds(552, 26, 404, 80);
		panel.add(panelnavegador);
		panelnavegador.setLayout(null);
		
		
		
		imaNav=new ImageIcon("imagenes/PRIMERO.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonprimero = new JButton("",imaNav);
		
		botonprimero.setBounds(12, 23, 87, 46);
		panelnavegador.add(botonprimero);
		
		
		imaNav=new ImageIcon("imagenes/IZQUIERDA.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonanterior = new JButton("",imaNav);
		
		botonanterior.setBounds(108, 23, 89, 46);
		panelnavegador.add(botonanterior);
		
		
		imaNav=new ImageIcon("imagenes/DERECHA.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonsiguiente = new JButton("",imaNav);
		
		botonsiguiente.setBounds(206, 23, 89, 46);
		panelnavegador.add(botonsiguiente);
		
		imaNav=new ImageIcon("imagenes/ULTIMO.jpg");
		imaNav=new ImageIcon(imaNav.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_DEFAULT));
		botonultimo = new JButton("",imaNav);
		
		botonultimo.setBounds(305, 23, 89, 46);
		panelnavegador.add(botonultimo);
		
		panellibros = new JPanel();
		panellibros.setForeground(Color.WHITE);
		panellibros.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)), "Libros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panellibros.setBounds(25, 26, 503, 216);
		panel.add(panellibros);
		panellibros.setLayout(null);
		
		 labelidlibro = new JLabel("idLibro");
		 labelidlibro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelidlibro.setBounds(10, 26, 65, 14);
		panellibros.add(labelidlibro);
		
		 labeltitulo = new JLabel("Titulo");
		 labeltitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labeltitulo.setBounds(10, 51, 46, 14);
		panellibros.add(labeltitulo);
		
		 labelautor = new JLabel("Autor");
		 labelautor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelautor.setBounds(10, 76, 46, 14);
		panellibros.add(labelautor);
		
		 labeleditorial = new JLabel("Editorial");
		 labeleditorial.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labeleditorial.setBounds(10, 101, 46, 14);
		panellibros.add(labeleditorial);
		
		 labelisbn = new JLabel("ISBN");
		 labelisbn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelisbn.setBounds(10, 126, 46, 14);
		panellibros.add(labelisbn);
		
		 labelprestado = new JLabel("Prestado");
		 labelprestado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelprestado.setBounds(10, 153, 65, 14);
		panellibros.add(labelprestado);
		
		 labelfecha = new JLabel("Fecha Devolucion");
		 labelfecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelfecha.setBounds(10, 176, 116, 14);
		panellibros.add(labelfecha);
		
		cajaid = new JTextField();
		cajaid.setBounds(140, 22, 339, 22);
		panellibros.add(cajaid);
		cajaid.setColumns(10);
		
		cajatitulo = new JTextField();
		cajatitulo.setBounds(140, 47, 339, 22);
		panellibros.add(cajatitulo);
		cajatitulo.setColumns(10);
		
		cajaautor = new JTextField();
		cajaautor.setBounds(140, 72, 339, 22);
		panellibros.add(cajaautor);
		cajaautor.setColumns(10);
		
		cajaeditorial = new JTextField();
		cajaeditorial.setBounds(140, 97, 339, 22);
		panellibros.add(cajaeditorial);
		cajaeditorial.setColumns(10);
		
		cajaisbn = new JTextField();
		cajaisbn.setBounds(140, 122, 339, 22);
		panellibros.add(cajaisbn);
		cajaisbn.setColumns(10);
		
		cajafecha = new JTextField();
		cajafecha.setBounds(140, 173, 339, 22);
		panellibros.add(cajafecha);
		cajafecha.setColumns(10);
		
		checkprestado = new JCheckBox("");
		checkprestado.setHorizontalAlignment(SwingConstants.LEFT);
		checkprestado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		checkprestado.setVerticalAlignment(SwingConstants.BOTTOM);
		checkprestado.setBounds(139, 145, 46, 22);
		panellibros.add(checkprestado);
		
		cajafiltro = new JTextField();
		cajafiltro.setBounds(167, 402, 157, 22);
		panel.add(cajafiltro);
		cajafiltro.setColumns(10);
		
		botonfiltrar = new JButton("Filtrado Autores");
		botonfiltrar.setBounds(25, 401, 132, 23);
		panel.add(botonfiltrar);
		
		 comboBox = new JComboBox();
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar...", "Destino", "Planeta", "Alfaguara"}));
		comboBox.setBounds(167, 435, 157, 22);
		panel.add(comboBox);
		
		JLabel labeledit = new JLabel("Filtrado por editoriales:");
		labeledit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labeledit.setBounds(25, 438, 132, 14);
		panel.add(labeledit);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				puntero=table.rowAtPoint(e.getPoint());
				try {
					mostrardatos();
				} catch (ClassNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (IsbnIncorrecto e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
	}


private void cargardatos() throws ClassNotFoundException, SQLException, IsbnIncorrecto, ParseException {
	
	dtm.setRowCount(0);
	dtm.setColumnCount(0);
	dtm.setColumnIdentifiers(titulos);
	

		for(int i=0;i<controlador.listagetlibros.size();i++){
			Libros lib=controlador.listagetlibros.get(i);
			
				Object[]fila={lib.getIdLibros(),lib.getTitulo(),lib.getAutor(),
						lib.getEditorial().toString(),lib.getIsbn(),lib.isPrestado(),lib.getFechaDevolucion()};
				dtm.addRow(fila);		
		}

}


private void mostrardatos() throws SQLException, ClassNotFoundException, IsbnIncorrecto, ParseException {
	
	Libros libro=controlador.listagetlibros.get(puntero);
	

		cajaid.setText(Integer.toString(libro.getIdLibros()));
		cajatitulo.setText(libro.getTitulo());
		cajaautor.setText(libro.getAutor());
		cajaeditorial.setText(libro.getEditorial());
		cajaisbn.setText(libro.getIsbn());
		checkprestado.setSelected(libro.isPrestado());
		cajafecha.setText(libro.getFechaDevolucion().toString());
		

	

//		cajaautor.setText(libro.getAutor());
//		
//		if(libro.getSexo().equals("hombre")){
//			radiohombre.setSelected(true);
//		}
//		else if(libro.getSexo().equals("mujer")){
//			radiomujer.setSelected(true);
//		}
//
//		combo.setSelectedItem(libro.getPoblacion());
//		checkprestado.setSelected(libro.isPrestado());
	


}
}
