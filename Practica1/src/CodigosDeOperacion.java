import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


public class CodigosDeOperacion {
	
	String instruccion;
	ArrayList <ModosDireccionamiento> lista;
	Iterator <ModosDireccionamiento> iterador;
	
	
	CodigosDeOperacion(String instruccion,StringTokenizer separador){
		
		this.instruccion = instruccion;
		lista = new ArrayList<ModosDireccionamiento>();
		insertarModo(separador);		
	}
	
	void mostrarCodigo(){
		ModosDireccionamiento aux;
		System.out.println(instruccion);
		iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		aux.mostrarModo();
    	}
	}
	
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
	
	String regresarInstruccion(){
		return instruccion;
	}

}
