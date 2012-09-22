/*
 * 
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.table.DefaultTableModel;


// TODO: Auto-generated Javadoc
/**
 * The Class InterpretarLinea.
 */
public class InterpretarLinea {

	//atributos
	
	/** The etiqueta. */
	String etiqueta;
	
	/** The codop. */
	String codop;
	
	/** The operando. */
	String operando;
	
	/** The modo. */
	String modo;
	
	/** The tabop. */
	Tabop tabop;
	
	/** The err. */
	Errores err;
    
    /** The file nam. */
    String fileNam;
    
    /** The fw. */
    FileWriter fw;
    
    /** The pw. */
    PrintWriter pw;
    
    /** The ints. */
    DefaultTableModel ints;
    
    /** The error. */
    boolean error;

	
	/**
	 * Instantiates a new interpretar linea.
	 */
	InterpretarLinea(){
		
		try {
			tabop = new Tabop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Crear archivo.
	 *
	 * @param direccion donde se encuentra el archivo asm
	 * @param ints tabla para el archivo ints
	 * @param errores para el archivo errores
	 */
	void crearArchivo(String direccion,DefaultTableModel ints,DefaultTableModel errores){
		try {
			this.ints=ints;
			err = new Errores(errores);
			err.crearArchivo(direccion);
			direccion = direccion.replace(".asm", ".inst");
	        fw = new FileWriter(direccion, false);
	        pw = new PrintWriter(fw);
	        pw.println(String.format("%-8s  %-10s  %-10s  %-10s %s","LINEA","ETIQUETA","CODOP","OPERANDO","MODO"));
	        pw.println(".......................................................................................");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
	}
	
	/**
	 * Analizar linea.
	 *
	 * @param linea a analizar
	 * @param contador es el numero de linea
	 * @return true, if n error
	 */
	boolean analizarLinea(String linea,int contador){
		
		etiqueta = new String("NULL");
		codop = new String("NULL");
		operando = new String("NULL");
		modo = null;
			
		if (!linea.isEmpty()&&linea.charAt(0)!=';')
		{
			linea=eliminarComentarios(linea);
			StringTokenizer tokens = new StringTokenizer(linea);
			int menu=tokens.countTokens();
			Character primero = linea.charAt(0);
			error=true;
			
			switch(menu){
			
				case 0:
						return false; 
				case 1:
					if (primero.compareTo(' ') ==  0 || primero.compareTo('\t') == 0){
						error = analisis(linea,1,2,contador);
					}
					else{
						err.resultado(3,1, contador);
						return false;
					}
					break;
				case 2:
					if (primero.compareTo(' ') !=  0 && primero.compareTo('\t') != 0){
						error = analisis(linea,0,2,contador);
					}
					
					else{
						error = analisis(linea,1,3,contador);
					}	
					
					break;
					
				case 3:
					if (primero.compareTo(' ') !=  0 && primero.compareTo('\t') != 0){
						error = analisis(linea,0,3,contador);
					}
					
					else{
						err.resultado(3,1, contador);
						return false;
					}
					break;
					
				
				default: 
					err.resultado(3,0, contador);
					return false;
			
			}// fin del switch
			
			/*if(etiqueta.compareTo("NULL")==0 && codop.compareTo("NULL") == 0 && operando.compareTo("NULL") == 0){
				err.resultado(3,1, contador);
				return false;			
			}*/
			
			if(error == true){
				return false;
			}	
			
			else
				return true;
		}// fin del if
		
		else 
			return false;
		
	}
	
	/**
	 * Eliminar comentarios.
	 *
	 * @param linea
	 * @return linea sin comentarios
	 */
	String eliminarComentarios(String linea){
		
		StringTokenizer sin_comentarios = new StringTokenizer(linea, ";");
		return sin_comentarios.nextToken();
	}
	
	/**
	 * Analisis.
	 *
	 * @param linea a analizar
	 * @param inicio 0 - Etique, 1 - Codop, 2 - Operando 
	 * @param fin 1 - Etique, 2 - Codop, 3 - Operando
	 * @param contador no de linea
	 * @return true, if successful
	 */
	boolean analisis(String linea, int inicio, int fin, int contador){
		
		StringTokenizer tokens = new StringTokenizer(linea);
		StringTokenizer aux_modo;
		String token;
		boolean error_oper=true;
	
		for (int aux = inicio ; aux < fin ; aux++ ){
			token = tokens.nextToken();
			
			switch(aux){
			
			case 0:etiqueta = Automata.analizar(token,err,contador);
					if (etiqueta.compareTo("NULL")==0){
						return true;
					}
					break;
			case 1:codop = Automata.analizar(token,modo,tabop,err,contador);
					if (codop.compareTo("NULL")==0){
						return true;
					}
					else{
						if(codop.contains("|"))
						{
							aux_modo = new StringTokenizer(codop,"|");
							codop = aux_modo.nextToken();
							modo  = aux_modo.nextToken();
							error_oper = Boolean.parseBoolean(aux_modo.nextToken());
							if (contador == 8){
								System.out.println("error _oper = "+ error_oper);
							}
							if ( error_oper == true && fin < 3){
								err.resultado(2,1,contador);
								return true;
							}
						}
				
					}
					break;
			case 2:operando = Automata.analizar(token);
					if (error_oper == false && operando.compareTo("NULL")!=0){
						err.resultado(2,0,contador);
						return true;
					}
					
					break;
			}// fin del switch
						
		}// fin del for
		return false;
	}
	

	/**
	 * Resultado.
	 *
	 * @param contador No. de linea
	 */
	public void resultado(int contador) {

			Object[] fila = new Object[5];
			fila[0]=contador; 
			fila[1]=etiqueta;
			fila[2]=codop;
			fila[3]=operando;
			fila[4]=modo;
			ints.addRow(fila);
			pw.println(String.format("%-8s  %-10s  %-10s  %-10s %s",contador,etiqueta,codop,operando,modo));
		
	}
	
	
	/**
	 * Cerrar archivo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	    err.cerrarArchivo();
	}
	
	/**
	 * Validar end.
	 *
	 * @return true, if successful
	 */
	boolean validarEND(){
		if (codop.compareToIgnoreCase("END")==0){
			return true;
		}
		else return false;
	}
	
}
