import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Tabop {
	
	ArrayList<CodigosDeOperacion> lista;
	Iterator<CodigosDeOperacion> iterador;
	RandomAccessFile archivo;
	File tabop;
	
	Tabop() throws IOException{
		lista = new ArrayList<CodigosDeOperacion>();
		tabop = new File("./Tabop.txt");
		abrirArchivo();
		leerArchivo();
		mostrarTabop();
	}
	
	void abrirArchivo() throws IOException{
    	archivo = new RandomAccessFile("Tabop.txt", "r");	   
    }// fin del metodo
    
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
    
    void mostrarTabop(){
    	CodigosDeOperacion aux;
    	iterador = lista.listIterator();
    	while(iterador.hasNext()){
    		aux= iterador.next();
    		aux.mostrarCodigo();
    		System.out.println();
    	}

    }
    
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
