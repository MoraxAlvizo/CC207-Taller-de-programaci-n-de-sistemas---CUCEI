/*
 * 
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class Tabop.
 */
public class Tabop {
	
	/** The lista. */
	ArrayList<CodigosDeOperacion> lista;
	
	/** The iterador. */
	Iterator<CodigosDeOperacion> iterador;
	
	/** The archivo. */
	RandomAccessFile archivo;
	
	/** The tabop. */
	File tabop;
	
	/**
	 * Instantiates a new tabop.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Tabop() throws IOException{
		lista = new ArrayList<CodigosDeOperacion>();
		tabop = new File("./Tabop.txt");
		abrirArchivo();
		leerArchivo();
	}
	
	/**
	 * Abrir archivo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void abrirArchivo() throws IOException{
    	archivo = new RandomAccessFile("Tabop.txt", "r");	   
    }// fin del metodo
    
    /**
     * Leer archivo.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void leerArchivo()throws IOException{
    	
    	String linea;
    	CodigosDeOperacion aux = null;
    	String nombreCodopAnterior = "null";
    	StringTokenizer separador; 
    	String codop;
    	
    	while((linea=archivo.readLine())!=null){
    		if(!linea.isEmpty()){
    			separador = new StringTokenizer(linea,"|");
    			codop = separador.nextToken();
    			if (codop.compareTo(nombreCodopAnterior)==0){
    				aux.insertarModo(separador);
    			}
    			else {
    				aux = new CodigosDeOperacion(codop,separador);
    				lista.add(aux);
    				nombreCodopAnterior = codop;
    			}		
    		}
    		else continue;
    	}	
    	archivo.seek(0);
    }
    
    /**
     * Mostrar tabop.
     */
    void mostrarTabop(){
    	CodigosDeOperacion aux;
    	iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		aux.mostrarCodigo();
    		System.out.println();
    	}

    }
    
    /**
     * Busqueda binaria.
     *
     * @param codop que se va a buscar
     * @return the codigos de operacion
     */
    CodigosDeOperacion busquedaBinaria(String codop){
    	
    	CodigosDeOperacion aux = null;
    	int izq=0,der=lista.size()-1,cen;
    	boolean bandera = false;
    	while (izq <= der && bandera == false){
    		cen=(int)((izq+der)/2);
    		aux=lista.get(cen);
    		if (codop.compareTo(aux.regresarInstruccion())==0){
    			return aux;
    		}
    		
    		else if (codop.compareTo(aux.regresarInstruccion())>0){
    			izq = cen + 1;
    		}
    		
    		else{
    			der = cen - 1;
    		}
    	}
    	
    
    	return null;
    }
    

}
