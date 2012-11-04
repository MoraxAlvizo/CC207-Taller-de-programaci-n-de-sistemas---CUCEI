import java.util.ArrayList;
import java.util.Iterator;


class Linea {

	Integer nolinea;
    String contador;
    Etiqueta etiqueta;
    CodigosDeOperacion codop;
    Directivas directiva;
    String operando;
    ModosDireccionamiento modo;
    String codigomaquina;
	
    Linea(String linea, Tabop tabop, TablaSimbolos ts){

    	String cod;
		ArrayList<String> separador = Automata.separarTokens(linea);
		Iterator<String> iterador = separador.iterator();
		nolinea = new Integer(iterador.next());
	    contador = iterador.next();
	    etiqueta =  ts.regresarEtiqueta(iterador.next());
	    
	    cod = iterador.next();
	    directiva = new Directivas(cod);
	    if(directiva.regresarDirectiva() == -1)
	    	codop = tabop.busqueda(cod);
	    
	    operando = iterador.next();
	    if(directiva.regresarDirectiva() == -1)
	    	modo = codop.regresarModo(iterador.next());
	    else modo = null;
    }
    
    ModosDireccionamiento regresarModo(){
    	return modo;
    }
    
    String regresarOperando(){
    	return operando;
    }
    
    Etiqueta regresarEtiqueta(){
    	return etiqueta;
    }
    
    void asignarCodigoMaquina(String codigomaquina){
    	this.codigomaquina = codigomaquina;
    }

	Integer regresarNoLinea() {
		return nolinea;
	}
	
	String regresarOriginal(){
		Object[] fila = new Object[4];
		
		fila[0] = nolinea;
		
		if(etiqueta != null)fila[1]=etiqueta.regresarNombre();
		else fila[1] = "";
		
		if(codop != null)fila[2]=codop.regresarInstruccion();
		else fila[2]=directiva.regresarDirectiva();
		
		if(operando != null)fila[3]=operando;
		else fila[3]= "";
		
		return String.format("Linea %-5s: %-10s %-10s %-20s Eliminada",fila);
		
	}
	
	void asignarContador(Integer conloc){
		this.contador = Integer.toHexString(conloc);
	}
	
	String regresarContador(){
		return contador;
	}
	
	Integer regresarDirectiva(){
		return directiva.regresarDirectiva();
	}
    
	void actualizarEtiqueta(){
		if(etiqueta != null)
			etiqueta.actualizarConloc(this.contador);
	}
    
}
