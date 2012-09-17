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
	Automata[] analizador;
	Tabop tabop;
	Errores err;
    String fileNam;
    FileWriter fw;
    PrintWriter pw;
    int error;

	
	InterpretarLinea(){
		
		// automata completo
		analizador = new Automata[3];
		analizador[0] = new AutomataEtiqueta();
		analizador[1] = new AutomataCodop();
		analizador[2] = new AutomataOperandos();
		
		try {
			tabop = new Tabop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	void crearArchivo(String direccion){
		try {
			err = new Errores();
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
	
	boolean analizarLinea(DefaultTableModel a,DefaultTableModel errores,String linea,int contador){
		
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
			System.out.println(contador + "\t cantidad tokens :" +menu+"\n");
			error=-1;
			
			switch(menu){
			
				case 0:
						return false; 
				case 1:
					if (primero.compareTo(' ') ==  0 || primero.compareTo('\t') == 0){
						error = analisis(linea,1,2);
					}
					break;
				case 2:
					if (primero.compareTo(' ') !=  0 && primero.compareTo('\t') != 0){
						error=analisis(linea,0,2);

					}
					
					else{
						error = analisis(linea,1,3);
					}	
					
					break;
					
				case 3:
					if (primero.compareTo(' ') !=  0 && primero.compareTo('\t') != 0){
						error = analisis(linea,0,3);
					}
					break;
					
				
				default: error = 3;
			
			}// fin del switch
			if(error!=-1){
				err.resultado(errores, error, contador);
				return false;
			}
			
			else if(etiqueta.compareTo("NULL")==0 && codop.compareTo("NULL") == 0 && operando.compareTo("NULL") == 0){
				err.resultado(errores, 3, contador);
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
	
	int analisis(String linea, int inicio, int fin){
		
		StringTokenizer tokens = new StringTokenizer(linea);
		StringTokenizer aux_modo;
		String token;
	
		for (int aux = inicio ; aux < fin ; aux++ ){
			token = tokens.nextToken();
			
			switch(aux){
			
			case 0:etiqueta = analizador[aux].analizar(token);
					if (etiqueta.compareTo("NULL")==0){
						return aux;
					}
					break;
			case 1:codop = analizador[aux].analizar(token,modo,tabop);
					if (codop.compareTo("NULL")==0){
						return aux;
					}
					else{
						if(codop.contains("|"))
						{
							aux_modo = new StringTokenizer(codop,"|");
							codop = aux_modo.nextToken();
							modo  = aux_modo.nextToken();
						}
				
					}
					break;
			case 2:operando = analizador[aux].analizar(token);
					if (operando.compareTo("NULL")==0){
						return aux;
					}
					break;
			}// fin del switch
						
		}// fin del for
		return -1;
	}
	

	public void resultado(DefaultTableModel a, int contador) {

			Object[] fila = new Object[5];
			fila[0]=contador; 
			fila[1]=etiqueta;
			fila[2]=codop;
			fila[3]=operando;
			fila[4]=modo;
			a.addRow(fila);
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
