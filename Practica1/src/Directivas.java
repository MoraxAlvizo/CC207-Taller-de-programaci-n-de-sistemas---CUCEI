import java.util.ArrayList;

class Directivas {

	Integer nodirectiva;
	String nombre;
	static ArrayList<Integer> estadosvalidos = new ArrayList<Integer>();
	
	
	static int[][] automata={ //O  R  G  E  N  D  W  B  C  S  .  F  M  Q  U
							  { 1,23,-1, 4,-1, 7,-1,-1,-1,-1,-1,14,-1,-1,-1},  //0
							  {-1,2 ,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //1
							  {-1,-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //2
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //3  ORG
							  {-1,-1,-1,-1, 5,-1,-1,-1,-1,-1,-1,-1,-1,28,-1},  //4 
							  {-1,-1,-1,-1,-1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //5
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //6  END
							  {-1,-1,-1,-1,-1,-1, 8, 9,10,19,-1,-1,-1,-1,-1},  //7
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //8	DW
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //9	DB	
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,-1,-1,-1,-1},  //10
							  {-1,-1,-1,-1,-1,-1,12,13,-1,-1,-1,-1,-1,-1,-1},  //11
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //12	DC.W
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //13	DC.B
							  {-1,-1,-1,-1,-1,18,-1,-1,15,-1,-1,-1,-1,-1,-1},  //14
							  {-1,-1,-1,-1,-1,-1,-1,17,16,-1,-1,-1,-1,-1,-1},  //15
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //16	FCC
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //17	FCB
							  {-1,-1,-1,-1,-1,-1,-1,27,-1,-1,-1,-1,-1,-1,-1},  //18
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,20,-1,-1,-1,-1},  //19 DS
							  {-1,-1,-1,-1,-1,-1,22,21,-1,-1,-1,-1,-1,-1,-1},  //20
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //21	DS.B
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //22	DS.W
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,24,-1,-1},  //23
							  {-1,-1,-1,-1,-1,-1,26,25,-1,-1,-1,-1,-1,-1,-1},  //24
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //25	RMB
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //26	RMW
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},  //27	FDB
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,29},  //28
							  {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}}; //29	EQU
	
	Directivas(String codop){
		nombre = codop;
		estadosvalidos.add(3);
		estadosvalidos.add(6);
		estadosvalidos.add(8);
		estadosvalidos.add(9);
		estadosvalidos.add(12);
		estadosvalidos.add(13);
		estadosvalidos.add(16);
		estadosvalidos.add(17);
		estadosvalidos.add(19);
		estadosvalidos.add(21);
		estadosvalidos.add(22);
		estadosvalidos.add(25);
		estadosvalidos.add(26);
		estadosvalidos.add(27);
		estadosvalidos.add(29);
		nodirectiva = isDirectiva(codop);
		
	}
	
	static Integer isDirectiva(String codop){
		int estado=0;
		try{
			for(char aux:codop.toCharArray()){
				switch(aux){
				
				case 'O':case'o':estado = automata[estado][0];break;
				case 'R':case'r':estado = automata[estado][1];break;
				case 'G':case'g':estado = automata[estado][2];break;
				case 'E':case'e':estado = automata[estado][3];break;
				case 'N':case'n':estado = automata[estado][4];break;
				case 'D':case'd':estado = automata[estado][5];break;
				case 'W':case'w':estado = automata[estado][6];break;
				case 'B':case'b':estado = automata[estado][7];break;
				case 'C':case'c':estado = automata[estado][8];break;
				case 'S':case's':estado = automata[estado][9];break;
				case '.':		 estado = automata[estado][10];break;
				case 'F':case'f':estado = automata[estado][11];break;
				case 'M':case'm':estado = automata[estado][12];break;
				case 'Q':case'q':estado = automata[estado][13];break;
				case 'U':case'u':estado = automata[estado][14];break;
				default: estado = -1;
				}
			}
		}catch(Exception e){return -1;}
		
		if(estadosvalidos.indexOf(estado)!=-1){
			return estado;
		}
		else return -1;
		
	}
	
	Integer regresarDirectiva(){
		return nodirectiva;
	}
	
	String regresarNombreDirectiva(){
		return nombre;
	}
	
	Boolean validarORG(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		try {
			char base = operando.charAt(0);
			switch(base){
			
			case '$':operando = operando.replace("$", "$0");break;
			case '@':operando = operando.replace("@", "@0");break;
			case '%':operando = operando.replace("%", "%0");break;
			
			}
	
			int nodecimal = Automata.cambiarABaseDecimal(operando);
			if(Automata.validarNumero(nodecimal,16,65535,0)){
				c.asignarDireccionInicial(nodecimal);
				return true;
			}
			else {
				err.resultado(9, 1, linea);
				return false;
			}
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
		
	}

	Boolean validarEQU(String operando,Errores err, int linea, String etiqueta,ContadorDeLocalidades c){
		
		try {
			if(etiqueta.compareTo("NULL")!=0){
				int nodecimal = Automata.cambiarABaseDecimal(operando);
				if(Automata.validarNumero(nodecimal,16,65535,0)){
					c.asignarContadorEQU(nodecimal);
					return true;
				}
				else {
					err.resultado(9, 1, linea);
					return false;
				}
			}
			else{
				err.resultado(10, 1, linea);
				return false;
			}
			
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
		
	}
	
	Boolean validarDirectivasConstantes1Byte(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		try {
			int nodecimal = Automata.cambiarABaseDecimal(operando);
			if(Automata.validarNumero(nodecimal,16,255,0)){
				c.bytesPorIncrementar(1);
				return true;
			}
			else {
				err.resultado(9, 1, linea);
				return false;
			}
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
	}
	
	Boolean validarDirectivasConstantes2Byte(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		try {
			int nodecimal = Automata.cambiarABaseDecimal(operando);
			if(Automata.validarNumero(nodecimal,16,65535,0)){
				c.bytesPorIncrementar(2);
				return true;
			}
			else {
				err.resultado(9, 1, linea);
				return false;
			}
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
	}
	
	Boolean validarFCC(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		int longitud=0;
		int estado=0;
							// "   A  O  \
		int automataFCC[][]= {{ 1,-1,-1,-1}, //0
							  { 2, 1,-1, 3}, //1
							  {-1,-1,-1,-1}, //2
							  { 1, 1,-1, 1}, //3
							  	};
		
		try{
			for(char aux:operando.toCharArray()){
				if(aux=='\"'){
					if(estado == 3){
						longitud++;
					}
					estado = automataFCC[estado][0];
				}
				else if(aux=='\\'){
					if(estado == 3)longitud++;
					estado = automataFCC[estado][3];
				}
				else if((int)aux>=32 && (int)aux <= 255){
					estado = automataFCC[estado][1];
					longitud++;
				}
				else{
					estado = automataFCC[estado][2];
				}
				
			}
			
		}catch(Exception e){
			err.resultado(9, 2, linea);
			return false;}
		
		if(estado ==2){
			c.bytesPorIncrementar(longitud);
			return true;
		}
		else{
			err.resultado(9, 0, linea);
			return false;
		}		
		
	}
	
	Boolean validarDirectivasEspacio1Byte(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		try {
			int nodecimal = Automata.cambiarABaseDecimal(operando);
			if(Automata.validarNumero(nodecimal,16,65535,0)){
				c.bytesPorIncrementar(nodecimal);
				return true;
			}
			else {
				err.resultado(9, 1, linea);
				return false;
			}
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
	}
	
	Boolean validarDirectivasEspacio2Byte(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		try {
			int nodecimal = Automata.cambiarABaseDecimal(operando);
			if(Automata.validarNumero(nodecimal,16,65535,0)){
				c.bytesPorIncrementar(nodecimal*2);
				return true;
			}
			else {
				err.resultado(9, 1, linea);
				return false;
			}
		} catch (Exception e) {
			err.resultado(9, 0, linea);
			return false;
		}
	}
	
	Boolean validarEND(String operando,Errores err, int linea, ContadorDeLocalidades c){
		
		return true;
	}
	
}
