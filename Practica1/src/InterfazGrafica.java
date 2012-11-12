/*
 * 
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import java.awt.SystemColor;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTabbedPane; 
import java.awt.Font;
import java.io.IOException;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.ComponentOrientation;

// TODO: Auto-generated Javadoc
/**
 * La Clase InterfazGrafica. Clase encarga de toda la parte visual de programa y del manejo de eventos
 */
public class InterfazGrafica extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane panelConFichas;// crando herramienta de fichas 
	
	/** The content pane. */
	private JPanel contentPane;
	
	/** The tabla_errores. */
	private JTable tabla_errores;
	
	/** The tabla tabsim. */
	private JTable tabla_tabsim;
	
	/** The resultado. */
	DefaultTableModel resultado;
	
	/** The errores. */
	DefaultTableModel errores;
	
	/** The tabsim. */
	DefaultTableModel tabsim;
	
	/** The fc. */
	@SuppressWarnings("unused")
	private JFileChooser fc;
	
	/** The ensamblador. */
	public AnalizarArchivo ensamblador;
	
	/** The table_1. */
	private JTable table_1;
	
	/** The bandera_analisis. */
	boolean bandera_analisis;
	
	/**
	 * The Class MyRenderer.
	 */
	private class MyRenderer extends DefaultTableCellRenderer {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The background. */
		Color background;
		
		/** The foreground. */
		Color foreground;
		
		/**
		 * Instantiates a new my renderer.
		 *
		 * @param background the background
		 * @param foreground the foreground
		 */
		public MyRenderer (Color background,Color foreground) {
			super();
			this.background = background;
			this.foreground = foreground;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			comp.setBackground(background);
			comp.setForeground(foreground);
			return comp;
			}
		}

	/**
	 * Create the frame.
	 */
	public InterfazGrafica() {
		
		UIManager.put ("TabbedPane.selected", Color.DARK_GRAY);
		UIManager.put ("TabbedPane.borderHightlightColor", Color.GRAY);

		setIconImage(Toolkit.getDefaultToolkit().getImage("/home/omar/Imágenes/procesador-icono-8477-128.png"));
		
		try {
			ensamblador = new AnalizarArchivo ();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		setFont(new Font("Ubuntu", Font.ITALIC, 12));
		setResizable(false);
		setBackground(SystemColor.windowText);
		setTitle("CC207 TALLER PROGRAMACION DE SISTEMAS Practica 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 574);
		
		
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo ensamblador","asm");
		fc.setFileFilter(filter);
		fc.setForeground(Color.LIGHT_GRAY);
		fc.setBackground(Color.GRAY);
		
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelConFichas = new JTabbedPane();
		panelConFichas.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelConFichas.setBorder(null);
		panelConFichas.setFont(new Font("Courier 10 Pitch", Font.BOLD, 12));
		panelConFichas.setTabPlacement(JTabbedPane.BOTTOM);
		panelConFichas.setBackground(Color.darkGray);
		panelConFichas.setForeground(Color.white);
		panelConFichas.setBounds(470, 70, 618, 300);
		contentPane.add(panelConFichas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(SystemColor.activeCaption);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 71, 448, 300);
		contentPane.add(scrollPane);
		
		final JTextArea asm = new JTextArea();
		asm.setBorder(null);
		asm.setDisabledTextColor(SystemColor.activeCaption);
		asm.setEditable(false);
		scrollPane.setViewportView(asm);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setForeground(SystemColor.activeCaption);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBackground(SystemColor.activeCaption);

		panelConFichas.addTab("Archivo inst",null,scrollPane_1,"inst");
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setForeground(SystemColor.activeCaption);
		scrollPane_3.setBorder(null);
		scrollPane_3.setBackground(SystemColor.activeCaption);
		panelConFichas.addTab("Tab. simbolos",null,scrollPane_3,"tds");
		
		panelConFichas.setBackgroundAt(0,Color.LIGHT_GRAY);
		panelConFichas.setBackgroundAt(1,Color.LIGHT_GRAY);
		
		
		inicializarTablaAnalizar();
		inicializarTablaErrores();
		inicializarTablaTabsim();
		scrollPane_1.setViewportView(table_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 425, 1078, 114);
		contentPane.add(scrollPane_2);

		scrollPane_2.setViewportView(tabla_errores);
		scrollPane_3.setViewportView(tabla_tabsim);
		
		
		JButton btnAnalizar = new JButton("Analizar");
		btnAnalizar.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnAnalizar.setIconTextGap(1);
		btnAnalizar.setIcon(new ImageIcon("/home/omar/Imágenes/lupa (1).png"));
		btnAnalizar.setFocusPainted(false);
		btnAnalizar.setFocusTraversalKeysEnabled(false);
		btnAnalizar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnAnalizar.setBackground(Color.GRAY);
		btnAnalizar.setForeground(Color.WHITE);
		btnAnalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (bandera_analisis == false ){
					try {
						bandera_analisis = ensamblador.analizar(resultado,errores,tabsim);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				else
					JOptionPane.showMessageDialog( null , "Archivo ya analizado","ERROR DE ANALISIS" , JOptionPane.ERROR_MESSAGE);
		
			}
		});
		btnAnalizar.setBounds(942, 6, 146, 58);
		contentPane.add(btnAnalizar);
		
		JLabel lblArchivoAsm = new JLabel("ASM");
		lblArchivoAsm.setIcon(new ImageIcon("/home/omar/Imágenes/icono.png"));
		lblArchivoAsm.setForeground(new Color(255, 255, 255));
		lblArchivoAsm.setBounds(12, 12, 400, 47);
		contentPane.add(lblArchivoAsm);
		
		JLabel lblNewLabel = new JLabel("INST");
		lblNewLabel.setIcon(new ImageIcon("/home/omar/Imágenes/aceptar-verde-ok-si-icono-8925-48.png"));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(470, 12, 107, 47);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Errores");
		lblNewLabel_1.setIcon(new ImageIcon("/home/omar/Imágenes/icono_error.png"));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(20, 383, 192, 58);
		contentPane.add(lblNewLabel_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		menuBar.setForeground(Color.WHITE);
		menuBar.setBorder(UIManager.getBorder("MenuBar.border"));
		menuBar.setBackground(new Color(105, 105, 105));
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setForeground(SystemColor.controlHighlight);
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir . . . ");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int returnVal = fc.showOpenDialog(InterfazGrafica.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                
	                try {
	                	
	                	asm.setText("");
						ensamblador.abrirArchivo(fc.getSelectedFile());
						ensamblador.leerArchivo(asm);
						bandera_analisis = false;
						vaciarTablas();
						repaint(); // Para la grafica
						validate(); // Para los JComponents
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                //this is where a real application would open the file.
	               
	                
	            } else {
	            	System.out.println("Open command cancelled by user." );
	            }
			}
		});

		mnArchivo.add(mntmAbrir);
		//ManejadorEventos manejador = new ManejadorEventos();
	}
	
	
	/**
	 * Inicializar tabla analizar.
	 */
	public void inicializarTablaAnalizar(){
		
		MyRenderer r= new MyRenderer(Color.DARK_GRAY ,Color.LIGHT_GRAY );
		resultado = new DefaultTableModel();
		resultado.addColumn("No.");
		resultado.addColumn("ConLoc");
		resultado.addColumn("Etiqueta");
		resultado.addColumn("Codop");
		resultado.addColumn("Operando");
		resultado.addColumn("Modos");
		resultado.addColumn("CodMaq");
		table_1 = new JTable(resultado);
		table_1.setBorder(null);
		table_1.setGridColor(SystemColor.activeCaption);
		table_1.setSelectionBackground(SystemColor.textHighlight);
		table_1.setForeground(Color.WHITE);
		table_1.setBackground(SystemColor.activeCaption);
		table_1.setEnabled(false);
		table_1.getColumnModel().getColumn(0).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(0).setMaxWidth(30);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(30);
		table_1.getColumnModel().getColumn(1).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(1).setMaxWidth(60);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(2).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(2).setMaxWidth(60);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(3).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(3).setMaxWidth(60);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(4).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(4).setMaxWidth(200);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(5).setHeaderRenderer(r);
		table_1.getColumnModel().getColumn(5).setMaxWidth(60);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(6).setHeaderRenderer(r);
		
		

	}
	
	/**
	 * Inicializar tabla errores.
	 */
	public void inicializarTablaErrores(){
		MyRenderer r= new MyRenderer(Color.DARK_GRAY ,Color.LIGHT_GRAY);
		errores = new DefaultTableModel();
		errores.addColumn("Linea");
		errores.addColumn("Error");
		errores.addColumn("Descripción del error");
		tabla_errores = new JTable(errores);
		tabla_errores.setBorder(null);
		tabla_errores.setGridColor(SystemColor.activeCaption);
		tabla_errores.setSelectionBackground(Color.LIGHT_GRAY);
		tabla_errores.setForeground(Color.WHITE);
		tabla_errores.setBackground(SystemColor.activeCaption);
		tabla_errores.setEnabled(false);
		tabla_errores.getColumnModel().getColumn(0).setMaxWidth(50);
		tabla_errores.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabla_errores.getColumnModel().getColumn(0).setHeaderRenderer(r);
		tabla_errores.getColumnModel().getColumn(1).setMaxWidth(50);
		tabla_errores.getColumnModel().getColumn(1).setPreferredWidth(50);
		tabla_errores.getColumnModel().getColumn(1).setHeaderRenderer(r);
		tabla_errores.getColumnModel().getColumn(2).setHeaderRenderer(r);

	}
	
	public void inicializarTablaTabsim(){
		MyRenderer r= new MyRenderer(Color.DARK_GRAY ,Color.LIGHT_GRAY);
		tabsim = new DefaultTableModel();
		tabsim.addColumn("Etiqueta");
		tabsim.addColumn("Valor");
		tabla_tabsim = new JTable(tabsim);
		tabla_tabsim.setBorder(null);
		tabla_tabsim.setGridColor(SystemColor.activeCaption);
		tabla_tabsim.setSelectionBackground(Color.LIGHT_GRAY);
		tabla_tabsim.setForeground(Color.WHITE);
		tabla_tabsim.setBackground(SystemColor.activeCaption);
		tabla_tabsim.setEnabled(false);
		tabla_tabsim.getColumnModel().getColumn(0).setHeaderRenderer(r);
		tabla_tabsim.getColumnModel().getColumn(1).setHeaderRenderer(r);

	}
	
	/**
	 * Vaciar tablas.
	 */
	public void vaciarTablas(){
		while (resultado.getRowCount()!=0){
            resultado.removeRow(0);
		}
		while (errores.getRowCount()!=0){
            errores.removeRow(0);
		}
		while (tabsim.getRowCount()!=0){
            tabsim.removeRow(0);
		}
	}
}
