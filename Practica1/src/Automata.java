import java.util.ArrayList;
import java.util.Iterator;


// TODO: Auto-generated Javadoc
/**
 * La Clase Automata. Clase estatica que consta solo de 3 metodos para el analisis de 
 * los componetes que puede haber en la linea
 */
public class Automata {
	
	/**
	 * Analizar. analiza que el operando sea valido
	 *
	 * @param cadena a analizar
	 * @return the string si la cadena es un operando
	 */
	static String analizar(String cadena, Errores err, CodigosDeOperacion codop, int linea){
		ArrayList <ModosDireccionamiento> listaModos = codop.regresarListaModos();
		Iterator <ModosDireccionamiento> iterador = listaModos.listIterator();
		ModosDireccionamiento aux_modo;
		String modo;
		Integer error_base=0;
		
		ValidarModos.errori = -1;
		ValidarModos.errorj = -1;
		
		while(iterador.hasNext()){
			
			aux_modo = iterador.next();
			modo = aux_modo.regresarModo();
			if(modo.compareTo("IMM8")==0 && (error_base=ValidarModos.isIMM8(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("IMM16")==0 && (error_base=ValidarModos.isIMM16(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("DIR")==0 && (error_base=ValidarModos.isDIR(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("EXT")==0 && (error_base=ValidarModos.isEXT(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("IDX")==0 && (error_base=ValidarModos.isIDX(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("IDX1")==0 && (error_base=ValidarModos.isIDX1(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("IDX2")==0 && (error_base=ValidarModos.isIDX2(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("[IDX2]")==0 && (error_base=ValidarModos.isINDIRECTOIDX2(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("[D,IDX]")==0 && (error_base=ValidarModos.isDIDX(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("REL8")==0 && (error_base=ValidarModos.isREL8(cadena))==1){
				return cadena+"|"+modo;
			}
			else if(modo.compareTo("REL16")==0 && (error_base=ValidarModos.isREL16(cadena))==1){
				return cadena+"|"+modo;
			}
			
		}
		
		if(ValidarModos.errori != -1 && ValidarModos.errorj != -1){
			err.resultado(ValidarModos.errori,ValidarModos.errorj, linea);
			return "error";
		}
		
		else if( error_base == -1){
			err.resultado(8,1, linea);
			return "error";
		}
		
		return null;
	}
	
	static Integer cambiarABaseDecimal(String operando)throws Exception{
		
		Character base = operando.charAt(0);
		
		if(Character.isDigit(base) || base.compareTo('-')==0 || base.compareTo('+')==0 )
			return Integer.parseInt(operando);
		else 
			switch(base){
			
			case '%':return Integer.parseInt(operando.substring(1), 2);
			case '@':return Integer.parseInt(operando.substring(1), 8);
			case '$':return Integer.parseInt(operando.substring(1), 16);
			
			}
		
		return null;
	}
	
	static boolean validarRegistro(String registro){
		if ( registro.compareToIgnoreCase("X")  == 0) return true;
		else if ( registro.compareToIgnoreCase("Y")  == 0) return true;
		else if ( registro.compareToIgnoreCase("PC")  == 0) return true;
		else if ( registro.compareToIgnoreCase("SP")  == 0) return true;
		else return false;
	}

	
	/**
	 * Analizar. analiza que la una etiqueta sea valida
	 * 
	 *
	 * @param cadena a analizar
	 * @param err archivo de errores
	 * @param linea numero de linea 
	 * @return cadena if es una Etiqueta else NULL
	 */
	static String analizar(String cadena,Errores err, int linea){
		
		if(cadena.matches("[A-Za-z]+([0-9]*[A-Za-z]*[_]*)*")){
			if(cadena.length()<9)
				return cadena;
			else {
				err.resultado(0, 1, linea);
				return "NULL";
			}
		}
		else {
			err.resultado(0, 0, linea);
			return "NULL";
		}
		
	}
	
	static String analizarEtiquetaEnOperando(String cadena){
		
		if(cadena.matches("[A-Za-z]+([0-9]*[A-Za-z]*[_]*)*")){
			if(cadena.length()<9)
				return cadena;
			else {
				return null;
			}
		}
		else {
			return null;
		}
		
	}
	
	
	
	/**
	 * Analizar. Analiza que el codigo de operacion sea valido y ademas se encuentre entras las instrucciones
	 *
	 * @param cadena a analizar
	 * @param modo de direccionamiento 
	 * @param t - tabop
	 * @param err archivos de errores
	 * @param linea - numero de linea
	 * @return cadena if pertenece a CODOP else NULL
	 */
	static CodigosDeOperacion analizar(String cadena, String modo, Tabop t, Errores err, int linea){
		
		CodigosDeOperacion aux;
		
		if(cadena.matches("[A-Za-z]+[.]?[A-Za-z]*")){
			if(cadena.length()<6)
			{
				aux = t.busqueda(cadena);
				if(aux != null ){
					return aux;
				}
				
				else if(cadena.compareTo("ORG") == 0 || cadena.compareTo("END") == 0 ){
					return null;
				}
				
				else{
					err.resultado(1,2,linea);
					return null;
				}
				
			}			
			else{
				err.resultado(1, 1, linea);
				return null;
			}
		}
		else{
			err.resultado(1, 0, linea);
			return null;
		}
		
	}

	
}
