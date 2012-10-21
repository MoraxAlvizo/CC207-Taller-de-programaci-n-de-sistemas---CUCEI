
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
	
	/** Directiva. */
	Directivas directiva;
	
	/** Codigo de operacion. */
	CodigosDeOperacion codigooperacion;
	
	/** El operando. */
	String operando;
	
	/** El modo. */
	String modo;
	
	/** Modo. */
	ModosDireccionamiento modos;
	
	/** Contador de localidades. */
	ContadorDeLocalidades contadorlocalidades; 
	
	/** Modos de direccionamiento. */
	ModosDireccionamiento modo2;
	
	/** El tabop. atributp donde se encuentran todos los codigos de operaciones validos*/
	Tabop tabop;
	
	/** El err. atributo para el manejo de errores */
	Errores err;
	
	/** Tabla de simbolos. para el manejo de las etiquetas*/
	TablaSimbolos tabsim;
    
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
			contadorlocalidades = new ContadorDeLocalidades();
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
	void crearArchivo(String direccion,DefaultTableModel ints,DefaultTableModel errores,DefaultTableModel t){
		try {
			this.ints=ints;
			err = new Errores(errores);
			err.crearArchivo(direccion);
			tabsim = new TablaSimbolos(t);
			tabsim.crearArchivo(direccion);
			direccion = direccion.replace(".asm", ".inst");
	        fw = new FileWriter(direccion, false);
	        pw = new PrintWriter(fw);
	        pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %s","LINEA","CONTLOC","ETIQUETA","CODOP","OPERANDO","MODO"));
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
			ArrayList<String> lista = Automata.separarTokens(linea);
			//Iterator<String> tokens = lista.iterator();
			int menu=lista.size();
			/*
			StringTokenizer tokens = new StringTokenizer(linea);
			int menu=tokens.countTokens();*/
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
			
			
			if(error == true)
				return false;
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
		
		/*StringTokenizer tokens = new StringTokenizer(linea);*/
		String token;
		
		ArrayList<String> lista = Automata.separarTokens(linea);
		Iterator<String> tokens = lista.iterator();
	
		for (int aux = inicio ; aux < fin ; aux++ ){
			token = tokens.next();
			System.out.println(token);
			switch(aux){
			
			case 0:etiqueta = Automata.analizar(token,err,contador,tabsim);
					if (etiqueta.compareTo("NULL")==0){
						return true;
					}
					break;
			case 1:
				directiva = new Directivas(token);
				if(directiva.regresarDirectiva() == -1)
				{
					codigooperacion = Automata.analizar(token,modo,tabop,err,contador);
					if (codigooperacion == null){
						return true;
					}
					else{
						codop = codigooperacion.regresarInstruccion();
						modos = codigooperacion.regresarModos();
						if ( codigooperacion.regresarSiNecesitaOper() == true && fin < 3){
							if ( modos.regresarModo().compareTo("INH")!=0 && modos.regresarModo().compareTo("IMM")!=0){
								err.resultado(2,1,contador);
								return true;
							}
									
						}
						else if ( modos.regresarModo().compareTo("INH")==0 || modos.regresarModo().compareTo("IMM")==0){
							
							contadorlocalidades.bytesPorIncrementar(modos.regresarSumaTotal());
							modo = modos.regresarModo();
						}
					}
					
				}
				else{
					
					if(fin<3 && directiva.regresarDirectiva() != 6){
						err.resultado(11,2,contador);
						return true;
					}
					
					if(contadorlocalidades.regresarBanderaORG() && directiva.regresarDirectiva() == 3){
						err.resultado(11, 0, contador);
						return true;
					}
					contadorlocalidades.banderaEQU(directiva.regresarDirectiva());
					if(!contadorlocalidades.regresarBanderaORG())
						contadorlocalidades.banderaOrg(directiva.regresarDirectiva());
					if(!contadorlocalidades.regresarBanderaORG() && !contadorlocalidades.regresarbanderaEQU()){
						err.resultado(10,0,contador);
						contadorlocalidades.banderaOrg(3);
					}
					
				}

				break;
			case 2:
				if(directiva.regresarDirectiva() == -1){
					modos = Automata.analizar(token,err,codigooperacion,contador);
					if(!contadorlocalidades.regresarBanderaORG()){
						err.resultado(10,0,contador);
						contadorlocalidades.banderaOrg(3);
					}
					
					if (codigooperacion.regresarSiNecesitaOper() == false){
						err.resultado(2,0,contador);
						return true;
					}
					
					else if (modos != null && modos.regresarModo().compareTo("error")==0){
						return true;
					}
					
					else{
						
						try{
							operando = token;
							modo = modos.regresarModo();
							contadorlocalidades.bytesPorIncrementar(modos.regresarSumaTotal());
						}
						catch(Exception e){
							err.resultado(4, 0, contador);
							return true;
						}
						
					}
				}
				else{
					if(directiva.regresarDirectiva()==29){
						if(directiva.validarEQU(token, err, contador, etiqueta,contadorlocalidades)){
							operando = token;
						}
						else{
							return true;
						}
					}
					else{
						operando = Automata.analizar(token,err,directiva,contador,contadorlocalidades);
					}
				}
					
				break;
			}// fin del switch
						
		}// fin del for
		System.out.println(contadorlocalidades.contador);
		return false;
	}
	

	/**
	 * Resultado. Escribe el resultado el archivo .inst si no hubo ningun error
	 *
	 * @param contador No. de linea
	 */
	public void resultado(int contador) {

		boolean banderaTabsim = false;
			Object[] fila = new Object[6];
			fila[0]=contador; 
			if(contadorlocalidades.regresarContadorEQU() > -1){
				fila[1]=contadorlocalidades.regresarContadorEQUHexa();
				tabsim.resultado(etiqueta, contadorlocalidades.regresarContadorEQUHexa());
				contadorlocalidades.asignarContadorEQU(-1);
				banderaTabsim = true;
			}
			else{
				fila[1]=contadorlocalidades.regresarContadorORGHexa();
			}
			
			if(etiqueta.compareTo("NULL")!=0 && !banderaTabsim){
				tabsim.resultado(etiqueta,contadorlocalidades.regresarContadorORGHexa());
			}
			fila[2]=etiqueta;
			if(codop != null)fila[3]=codop;
			else fila[3]=directiva.regresarNombreDirectiva();
			fila[4]=operando;
			fila[5]=modo;
			ints.addRow(fila);
			
			
			if(codop != null)
				pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %s",contador,Integer.toHexString(contadorlocalidades.contador),etiqueta,codop,operando,modo));
			else
				pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %s",contador,Integer.toHexString(contadorlocalidades.contador),etiqueta,directiva.regresarNombreDirectiva(),operando,modo));
			
			contadorlocalidades.incrementarContador();
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
	    tabsim.cerrarArchivo();
	}
	
	/**
	 * Validar end. Valida que el codigo de operacion sea END
	 *
	 * @return true, if successful
	 */
	boolean validarEND(){
		if (directiva.regresarDirectiva()==6){
			return true;
		}
		else return false;
	}
	
}
