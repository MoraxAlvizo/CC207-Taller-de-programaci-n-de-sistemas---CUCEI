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
 * The Class InterpretarLinea. Clase que se encarga de interpretar una linea del archivo .asm y 
 * dar un resultado si se va al archivo .inst o al archivo de errores
 */
public class InterpretarLinea {

	
	/** La etiqueta. */
	String etiqueta;
	
	/** El codop. */
	String codop;
	
	/** Codigo de operacion. */
	CodigosDeOperacion codigooperacion;
	
	/** El operando. */
	String operando;
	
	/** El modo. */
	String modo;
	
	/** El tabop. atributp donde se encuentran todos los codigos de operaciones validos*/
	Tabop tabop;
	
	/** El err. atributo para el manejo de errores */
	Errores err;
    
    /** El file nam. direccion del archivo */
    String fileNam;
    
    /** El fw.*/
    FileWriter fw;
    
    /** El pw. */
    PrintWriter pw;
    
    /** El ints. Tabla el manejo del archivo .inst*/
    DefaultTableModel ints;
    
    /** El error. Si esta en true hubo un error si esta el false no */
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
	 * Crear archivo. Crea todos lo archivos necesarios para guardar los resultados del analisis
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
	 * Analizar linea. Metodo para definir el tipo de analisis
	 *
	 * @param linea a analizar
	 * @param contador es el numero de linea
	 * @return true, if n error
	 */
	boolean analizarLinea(String linea,int contador){
		
		etiqueta = new String("NULL");
		codop = null;
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
	 * Eliminar comentarios. Elimina los comentarios de la linea que se va a analizar
	 *
	 * @param linea
	 * @return linea sin comentarios
	 */
	String eliminarComentarios(String linea){
		
		StringTokenizer sin_comentarios = new StringTokenizer(linea, ";");
		return sin_comentarios.nextToken();
	}
	
	/**
	 * Analisis. Metodo para analizar la linea
	 *
	 * @param linea a analizar
	 * @param inicio 0 - Etique, 1 - Codop, 2 - Operando 
	 * @param fin 1 - Etique, 2 - Codop, 3 - Operando
	 * @param contador no de linea
	 * @return true, if successful
	 */
	boolean analisis(String linea, int inicio, int fin, int contador){
		
		StringTokenizer tokens = new StringTokenizer(linea);
		String token;
		StringTokenizer obtenermodo;
	
		for (int aux = inicio ; aux < fin ; aux++ ){
			token = tokens.nextToken();
			
			switch(aux){
			
			case 0:etiqueta = Automata.analizar(token,err,contador);
					if (etiqueta.compareTo("NULL")==0){
						return true;
					}
					break;
			case 1:codigooperacion = Automata.analizar(token,modo,tabop,err,contador);
					if (codigooperacion == null){
						if(token.compareToIgnoreCase("ORG") == 0 || token.compareToIgnoreCase("END") == 0){
							codop = token;
							break;
						}
						else{
							return true;
						}
					}
					else{
							codop = codigooperacion.regresarInstruccion();
							modo = codigooperacion.regresarModos();
							if ( codigooperacion.regresarSiNecesitaOper() == true && fin < 3){
								if ( modo.compareTo("INH")!=0 && modo.compareTo("IMM")!=0){
									err.resultado(2,1,contador);
									return true;
								}
									
							}
					}
					break;
			case 2:operando = Automata.analizar(token,err,codigooperacion,contador);
					if (codigooperacion.regresarSiNecesitaOper() == false){
						err.resultado(2,0,contador);
						return true;
					}
					
					else if (operando != null && operando.compareTo("error")==0){
						return true;
					}
					
					else{
						
						try{
							obtenermodo = new StringTokenizer(operando,"|");
							operando = obtenermodo.nextToken();
							modo = obtenermodo.nextToken();
						}
						catch(Exception e){
							err.resultado(4, 0, contador);
							return true;
						}
						
					}
					
					break;
			}// fin del switch
						
		}// fin del for
		return false;
	}
	

	/**
	 * Resultado. Escribe el resultado el archivo .inst si no hubo ningun error
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
	 * Cerrar archivo. Metodo que cierra todos los archivos
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void cerrarArchivo(boolean banderaEND) throws IOException{
		
		if(banderaEND == false)
			err.resultado(3,2,0);
	    pw.close();
	    fw.close();
	    err.cerrarArchivo();
	}
	
	/**
	 * Validar end. Valida que el codigo de operacion sea END
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
