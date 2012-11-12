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
	static ModosDireccionamiento analizar(String cadena, Errores err, CodigosDeOperacion codop, int linea){
		ArrayList <ModosDireccionamiento> listaModos = codop.regresarListaModos();
		Iterator <ModosDireccionamiento> iterador = listaModos.listIterator();
		ModosDireccionamiento aux_modo;
		String modo;
		
		ValidarModos.errori = -1;
		ValidarModos.errorj = -1;
		
		while(iterador.hasNext()){
			
			aux_modo = iterador.next();
			modo = aux_modo.regresarModo();
			if(modo.compareTo("IMM8")==0 && ValidarModos.isIMM8(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("IMM16")==0 && ValidarModos.isIMM16(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("DIR")==0 && ValidarModos.isDIR(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("EXT")==0 && ValidarModos.isEXT(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("IDX")==0 && ValidarModos.isIDX(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("IDX1")==0 && ValidarModos.isIDX1(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("IDX2")==0 && ValidarModos.isIDX2(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("[IDX2]")==0 && ValidarModos.isINDIRECTOIDX2(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("[D,IDX]")==0 && ValidarModos.isDIDX(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("REL8")==0 && ValidarModos.isREL8(cadena)==1){
				return aux_modo;
			}
			else if(modo.compareTo("REL16")==0 && ValidarModos.isREL16(cadena)==1){
				return aux_modo;
			}
			
		}
		
		if(ValidarModos.errori != -1 && ValidarModos.errorj != -1){
			err.resultado(ValidarModos.errori,ValidarModos.errorj, linea);
			return new ModosDireccionamiento("error");
		}
		
		else return null;
	}
	
	static ArrayList<String> separarTokens(String linea){
		ArrayList <String> listaTokens = new ArrayList<String>();
		String aux = "";
		int estado = 0;
		int [][] automata = {//  E	C  "  ;  \
							   { 0, 1, 2, 3, 1},	//0
							   { 0, 1, 1, 3, 1},	//1
							   { 2, 2, 0, 2, 4},	//2
							   {-1,-1,-1,-1,-1},	//3
							   { 2, 2, 2, 2, 2},	//4
							   			};
		
		for(char c:linea.toCharArray()){

			if(estado == 3)break;
			switch(c){
			case ' ':case'\t':
				if(estado == 1){
					listaTokens.add(aux);
					aux = "";
				}
				else if(estado ==2){
					aux +=c;
				}
				estado = automata[estado][0];
				break;
			case'\"':
				if(estado == 2){
					listaTokens.add(aux+=c);
					aux = "";
					estado = automata[estado][2];
				}
				else{
					estado = automata[estado][2];
					aux +=c;
				}	
				break;
			case ';':
				if(estado != 1 && estado !=0)
					aux +=c;
				estado = automata[estado][3];
				break;
			case '\\':
				estado = automata[estado][4];
				aux+=c;
				break;
			default:		 
				estado = automata[estado][1];
				aux +=c;
			}
			
		}
		if(!aux.isEmpty()){
			listaTokens.add(aux);
		}
		
		return listaTokens;
		
	}
	
	static String analizar(String cadena, Errores err, Directivas d, int linea,ContadorDeLocalidades c){
		
		boolean bandera;
		
		switch(d.regresarDirectiva()){
		case 3:				    
			bandera = d.validarORG(cadena,err,linea,c);
			if(bandera)c.banderaOrg(d.regresarDirectiva());
			break;
		case 9:case 13: 
			bandera = d.validarDirectivasConstantes1Byte(cadena,err,linea,c);
			break;
		case 8:case 12:case 27: 
			bandera = d.validarDirectivasConstantes2Byte(cadena,err,linea,c);
			break;
		case 16:			    
			bandera = d.validarFCC(cadena,err,linea,c);					   
			break;
		case 19:case 21:case 25:
			bandera = d.validarDirectivasEspacio1Byte(cadena,err,linea,c);   
			break;
		case 22:case 26:		
			bandera = d.validarDirectivasEspacio2Byte(cadena,err,linea,c);   
			break;
		case 17:
			bandera = d.validarDirectivasConstantes1Byte(cadena,err,linea,c);
			break;
		default: bandera = false;
		}

		if(bandera){
			c.banderaEQU(d.regresarDirectiva());
			return cadena;
		}
		else{
			return null;
		}
		
	}
	
	static Integer cambiarABaseDecimal(String operando)throws Exception{
		
		Character base = operando.charAt(0);
		
		if(Character.isDigit(base) || base.compareTo('-')==0)
			return Integer.parseInt(operando);
		else if(!operando.contains("-")){
			switch(base){
			
			case '%': 
				if(operando.charAt(1)=='1'){
					operando = operando.substring(1);
					while(operando.length() > 17){
						if(operando.charAt(0)=='0')return 65536;
						else if (operando.charAt(0)=='1')operando = operando.substring(1);
						else return null;
					}
					return Automata.complementoA2(Integer.parseInt(operando,2));
				}
					
				else return Integer.parseInt(operando.substring(1), 2);
			
			case '@':
				if(operando.charAt(1)=='7'){
					operando = operando.substring(1);
					while(operando.length() > 6){
						if(operando.charAt(0)!='7')return 65536;
						else operando = operando.substring(1);

					}
					return Automata.complementoA2(Integer.parseInt(operando,8));
				}
				else return Integer.parseInt(operando.substring(1), 8);
			case '$':
				if(operando.charAt(1)=='F' || operando.charAt(1)=='f'){
					operando = operando.substring(1);
					while(operando.length() > 5){
						if(operando.charAt(0)!='F')return 65536;
						else operando = operando.substring(1);
					}
					return Automata.complementoA2(Integer.parseInt(operando,16));
				}
				else return Integer.parseInt(operando.substring(1), 16);
		
			}
			
			return null;
		}	
		else return null;
	}
	
	static Boolean validarNumero(Integer operando,Integer base,Integer rango_positivo, Integer rango_negativo){
		try {
			if (operando >= rango_negativo && operando <= rango_positivo ){
				return true;
			}
			
			else return false;
		} catch (Exception e) {
			return false;
		}
		
		
	}
	
	static Integer complementoA2(Integer decimal){
		
		String complemento= Integer.toBinaryString(decimal);
		Integer bitesnecesarios;
			
		complemento = complemento.substring(1);
		bitesnecesarios = complemento.length();
		decimal = Integer.parseInt(complemento,2);
		decimal = ~decimal +1;
		complemento = Integer.toBinaryString(decimal);
		complemento = complemento.substring(complemento.length() - bitesnecesarios);
		decimal = Integer.parseInt(complemento,2);
		decimal = -decimal;	
		return decimal;
		
		
	}
	
	
	static boolean validarRegistro(String registro){
		if ( registro.compareToIgnoreCase("X")  == 0) return true;
		else if ( registro.compareToIgnoreCase("Y")  == 0) return true;
		else if ( registro.compareToIgnoreCase("PC")  == 0) return true;
		else if ( registro.compareToIgnoreCase("SP")  == 0) return true;
		else return false;
	}

	
	static boolean validarRegistroPrePost(String registro){
		if ( registro.compareToIgnoreCase("X")  == 0) return true;
		else if ( registro.compareToIgnoreCase("Y")  == 0) return true;
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
	static String analizar(String cadena,Errores err, int linea, TablaSimbolos t){
		
		if(cadena.matches("[A-Za-z]+([0-9]*[A-Za-z]*[_]*)*")){
			if(cadena.length()<9)
				if(t.validarEtiqueta(cadena))return cadena;
				else{
					err.resultado(11, 1, linea);
					return "NULL";
				}
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
