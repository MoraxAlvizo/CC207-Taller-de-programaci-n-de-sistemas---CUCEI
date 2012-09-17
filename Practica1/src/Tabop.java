import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

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
    	CodigosDeOperacion aux;
    	while((linea=archivo.readLine())!=null){
    		if(!linea.isEmpty()){
    			aux = new CodigosDeOperacion(linea);
        		lista.add(aux);
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
    	}

    }

}
