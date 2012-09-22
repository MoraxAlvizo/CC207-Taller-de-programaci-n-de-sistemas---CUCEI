/*
 * 
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.table.DefaultTableModel;


// TODO: Auto-generated Javadoc
/**
 * The Class Errores.
 */
public class Errores {
	
	/** The errores. */
	String[][] errores;
    
    /** The fw. */
    FileWriter fw;
    
    /** The pw. */
    PrintWriter pw;
    
    /** The err. */
    DefaultTableModel err;
	
	/**
	 * Instantiates a new errores.
	 *
	 * @param err tabla de errores
	 */
	Errores(DefaultTableModel err){
		this.err = err;
		
		errores = new String [4][3];
		
		//ERRORES DE ETIQUETA
		errores [0][0]= new String("ERROR: lexicografico en Etiqueta");
		errores [0][1]= new String("ERROR: tamaño excesivo en Etiqueta ");
		
		//ERRORES DE CODOP
		errores [1][0]= new String("ERROR: lexicografico en CODOP");
		errores [1][1]= new String("ERROR: tamaño excesivo en el Codigo de Operación");
		errores [1][2]= new String("ERROR: Codigo de operación no encontrado");
		
		
		//ERRORES DE OPERANDO
		errores [2][0]= new String("ERROR: No debe llevar operando");
		errores [2][1]= new String("ERROR: Debe llevar operando");
		
		//ERRORES DE LINEA
		errores [3][0]= new String("ERROR: exceso de tokens en la linea");
		errores [3][1]= new String("ERROR: formato de linea");
			 
	}
	
	/**
	 * Crear archivo.
	 *
	 * @param direccion donde se encuentra el archivo .asm
	 */
	void crearArchivo(String direccion){
		try {
			
			System.out.println("entro a errores");
			direccion = direccion.replace(".asm", ".err");
	        fw = new FileWriter(direccion, false);
	        pw = new PrintWriter(fw);
	        pw.println(String.format("%-8s  %-12s  %s","LINEA","ERROR","DESCRIPCION DEL ERROR"));
	        pw.println("..........................................................");
	      
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
	/**
	 * Resultado.
	 *
	 * @param donde fue el error: 0 - Etiqueta, 1 - Codop, 2 - Operando, 3 - linea
	 * @param no_error
	 * @param linea no. de linea
	 */
	public void resultado(int donde, int no_error,int linea) {
		Object[] fila = new Object[3];
		fila[0]=linea;
		fila[1]=donde +""+ no_error;
		fila[2]=errores[donde][no_error];

		err.addRow(fila);
		pw.println(String.format(String.format("%-8s  %-12s  %s",fila[0],fila[1],fila[2])));
		
	}
	
	/**
	 * Cerrar archivo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	}

}
