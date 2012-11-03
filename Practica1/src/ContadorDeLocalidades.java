
class ContadorDeLocalidades {
	
	Integer contador;
	Integer direccionInicial;
	Integer contadorEQU;
	Integer bytesPorAumentar;
	Boolean banderaORG;
	Boolean banderaEND;
	Boolean banderaEQU;
	
	
	ContadorDeLocalidades(){
		contador = 0 ;
		direccionInicial = 0;
		banderaORG = false;
		banderaEND = false;
		contadorEQU = -1;
		bytesPorAumentar =0;
	}
	
	void asignarDireccionInicial(Integer org){
		direccionInicial = org;
		contador = direccionInicial;
	}
	
	Boolean banderaOrg(int directiva){
		if(directiva == 3){
			banderaORG = true;
			return banderaORG;
		}
		return false;
	}
	
	Boolean banderaEQU(int directiva){
		if(directiva == 29){
			banderaEQU = true;
			return banderaEQU;
		}
		banderaEQU = false;
		return banderaEQU;
	}
	
	Boolean regresarbanderaEQU(){
		return banderaEQU;
	}
	
	Boolean regresarBanderaORG(){
		return banderaORG;
	}
	
	void bytesPorIncrementar(Integer bytes){
		bytesPorAumentar = bytes;
	}
	
	void incrementarContador(){
		contador +=bytesPorAumentar;
		bytesPorAumentar = 0;
	}
	
	void asignarContadorEQU(int bytes){
		contadorEQU= bytes;
	}
	
	String regresarContadorEQUHexa(){
		String contadorHexa = Integer.toHexString(contadorEQU);
		while(contadorHexa.length()<4)
			contadorHexa = "0" + contadorHexa; 
		
		return contadorHexa;
	}
	
	Integer regresarContadorEQU(){	
		return contadorEQU;
	}
	
	String regresarContadorORGHexa(){
		String contadorHexa = Integer.toHexString(contador);
		while(contadorHexa.length()<4)
			contadorHexa = "0" + contadorHexa; 
		
		return contadorHexa;
	}
	
	
}
