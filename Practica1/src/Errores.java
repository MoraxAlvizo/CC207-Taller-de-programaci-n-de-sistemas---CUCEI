/*
 * 
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.table.DefaultTableModel;


// TODO: Auto-generated Javadoc
/**
 * La Clase Errores. Esta clase es la que se encarga de escribir todos los errores 
 * que pueda haber durante el analisis del archivo
 */
public class Errores {
	
	/** Los errores. Automata para el manejo de errores*/
	String[][] errores;
    
    /** El fw. atributo para escribir un archivo*/
    FileWriter fw;
    
    /** El pw. atributo para leer un archivo*/
    PrintWriter pw;
    
    /** Los err. atributo para el manejo de la tabla de errores */
    DefaultTableModel err;
	
	/**
	 * Instantiates a new errores.
	 *
	 * @param err tabla de errores
	 */
	Errores(DefaultTableModel err){
		this.err = err;
		
		errores = new String [9][3];
		
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
		errores [3][2]= new String("ERROR: No se encontro el END");
		
		//ERRORES DE MODOS DE DIRECCIONAMIENTO
		errores [4][0]= new String("ERROR: Formato de operando no válido para ningún modo de direccionamiento");
		errores [4][1]= new String("ERROR: Operando fuera de rango para direccionamiento IMM8");
		errores [4][2]= new String("ERROR: Operando fuera de rango para direccionamiento IMM16");
		errores [5][0]= new String("ERROR: Operando fuera de rango para direccionamiento DIR");
		errores [5][1]= new String("ERROR: Operando fuera de rango para direccionamiento EXT");
		errores [5][2]= new String("ERROR: Operando fuera de rango para direccionamiento IDX");
		errores [6][0]= new String("ERROR: Operando fuera de rango para direccionamiento IDX1");
		errores [6][1]= new String("ERROR: Operando fuera de rango para direccionamiento IDX2");
		errores [6][2]= new String("ERROR: Operando fuera de rango para direccionamiento [IDX2]");
		errores [7][0]= new String("ERROR: Operando fuera de rango para direccionamiento [D,IDX]");
		errores [7][1]= new String("ERROR: Operando fuera de rango para direccionamiento REL8");
		errores [7][2]= new String("ERROR: Operando fuera de rango para direccionamiento REL16");
		errores [8][0]= new String("ERROR: Registro no encontrado");
		errores [8][1]= new String("ERROR: error en el formato del numero");
		errores [8][2]= new String("ERROR: error en el formato de etiqueta en el operando");
		
			 
	}
	
	/**
	 * Crear archivo. Metodo para crear el archivo .err
	 *
	 * @param direccion donde se encuentra el archivo .asm
	 */
	void crearArchivo(String direccion){
		try {
			
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
	 * @param no_error numero de error
	 * @param linea no. de linea
	 */
	public void resultado(int donde, int no_error,int linea) {
		Object[] fila = new Object[3];
		fila[0]=linea;
		fila[1]=donde +""+ no_error;
		fila[2]=errores[donde][no_error];

		err.addRow(fila);
		pw.println(String.format("%-8s  %-12s  %s",fila[0],fila[1],fila[2]));
		
	}
	
	/**
	 * Cerrar archivo. Metodo para cerras los archivos
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	}

}
