/*
 * 
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


// TODO: Auto-generated Javadoc
/**
 * La Clase CodigosDeOperacion. Es la clase al almacena un Codop y sus diferentes modos de direccionamiento
 */
public class CodigosDeOperacion {
	
	/** La instruccion. Almacena el nombre del Codop*/
	String instruccion;
	
	/** La lista. Almacena una lista de los distintos modos de direccionamiento del Codop*/
	ArrayList <ModosDireccionamiento> lista;
	
	/** El iterador. Atributo para recorrer la lista de modos de direccionamiento*/
	Iterator <ModosDireccionamiento> iterador;
	
	
	/**
	 * Instantiates a new codigos de operacion.
	 *
	 * @param instruccion - nombre de la instruccion 
	 * @param separador - Modo de direccionamiento a insertar
	 */
	CodigosDeOperacion(String instruccion,StringTokenizer separador){
		
		this.instruccion = instruccion;
		lista = new ArrayList<ModosDireccionamiento>();
		insertarModo(separador);		
	}
	
	/**
	 * Mostrar codigo. Muestra un codigo en consola y sus diferentes modos de direccionamiento
	 */
	void mostrarCodigo(){
		ModosDireccionamiento aux;
		System.out.println(instruccion);
		iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		aux.mostrarModo();
    	}
	}
	
	/**
	 * Insertar modo. Inserta un modo de direccionamiento a la lista
	 *
	 * @param separador - Modo de direccionamiento a insertar
	 * @return true, if successful
	 */
	boolean insertarModo(StringTokenizer separador){
		
		try{
			ModosDireccionamiento aux = new ModosDireccionamiento();
			aux.insertarModo(separador.nextToken());
			aux.insertarCodigoMaquina(separador.nextToken());
			aux.insertarCalculados(Integer.parseInt(separador.nextToken()));
			aux.insertarPorCalcular(Integer.parseInt(separador.nextToken()));
			aux.insertarSumaTotal(Integer.parseInt(separador.nextToken()));
			lista.add(aux);
			return true;
		}
		catch(Exception e){
			return false;
		}

	}
	
	/**
	 * Regresar modos. regresa una cadena con todos los modos de direccionamiento posibles para este Codop
	 *
	 * @return todos los modos de direccionamiento separados por ','
	 */
	ModosDireccionamiento regresarModos(){
		
		iterador = lista.listIterator();
    	if(iterador.hasNext()){
    		return iterador.next();
    	}
    	
    	else return null;
	}
	
	/**
	 * Regresar si necesita oper. Metodo para validar si el codigo de operacion necesita operandos o no
	 *
	 * @return true, if successful
	 */
	boolean regresarSiNecesitaOper(){
		
		ModosDireccionamiento aux;
		
		iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		if(aux.regresarPorCalcular() > 0){
    			return true;
    		}
    	}
    	return false;
    	
	}
	
	/**
	 * Regresar instruccion. Regresa el nombre del codop
	 *
	 * @return nombre de la instruccion
	 */
	String regresarInstruccion(){
		return instruccion;
	}
	
	ArrayList<ModosDireccionamiento> regresarListaModos(){
		return lista;
	}
	
	ModosDireccionamiento regresarModo(String modo){
		iterador = lista.listIterator();
		ModosDireccionamiento aux;
    	while(iterador.hasNext()){
    		aux = iterador.next();
    		if(aux.regresarModo().compareTo(modo)== 0){
    			return aux;
    		}
    	}
    	
    	return null;
	}

}
