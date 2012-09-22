import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.table.DefaultTableModel;


public class InterpretarLinea {

	//atributos
	
	String etiqueta;
	String codop;
	String operando;
	String modo;
	Tabop tabop;
	Errores err;
    String fileNam;
    FileWriter fw;
    PrintWriter pw;
    DefaultTableModel ints;
    boolean error;

	
	InterpretarLinea(){
		
		try {
			tabop = new Tabop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
	
	String eliminarComentarios(String linea){
		
		StringTokenizer sin_comentarios = new StringTokenizer(linea, ";");
		return sin_comentarios.nextToken();
	}
	
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
	
	
	public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	    err.cerrarArchivo();
	}
	
	boolean validarEND(){
		if (codop.compareToIgnoreCase("END")==0){
			return true;
		}
		else return false;
	}
	
}
