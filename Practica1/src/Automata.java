
public class Automata {
	
	static String analizar(String cadena){
		return cadena;
	}

	
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
	
	static String analizar(String cadena, String modo, Tabop t, Errores err, int linea){
		
		CodigosDeOperacion aux;
		
		if(cadena.matches("[A-Za-z]+[.]?[A-Za-z]*")){
			if(cadena.length()<6)
			{
				aux = t.busquedaBinaria(cadena);
				if(aux != null ){
					modo = aux.regresarModos();
					cadena = cadena + "|" +modo+"|"+aux.regresarSiNecesitaOper();
					return cadena;
				}
				
				else if(cadena.compareTo("ORG") == 0 || cadena.compareTo("END") == 0 ){
					return cadena;
				}
				
				else{
					err.resultado(1,2,linea);
					return "NULL";
				}
				
			}			
			else{
				err.resultado(1, 1, linea);
				return "NULL";
			}
		}
		else{
			err.resultado(1, 0, linea);
			return "NULL";
		}
		
	}

	
}
