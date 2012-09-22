/*
 * 
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


// TODO: Auto-generated Javadoc
/**
 * The Class CodigosDeOperacion.
 */
public class CodigosDeOperacion {
	
	/** The instruccion. */
	String instruccion;
	
	/** The lista. */
	ArrayList <ModosDireccionamiento> lista;
	
	/** The iterador. */
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
	 * Mostrar codigo.
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
	 * Insertar modo.
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
	 * Regresar modos.
	 *
	 * @return todos los modos de direccionamiento separados por ','
	 */
	String regresarModos(){
		ModosDireccionamiento aux;
		String modos="";
		
		iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		modos = modos + aux.regresarModo()+", ";
    	}
    	
    	return modos;
	}
	
	/**
	 * Regresar si necesita oper.
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
	 * Regresar instruccion.
	 *
	 * @return nombre de la instruccion
	 */
	String regresarInstruccion(){
		return instruccion;
	}

}
