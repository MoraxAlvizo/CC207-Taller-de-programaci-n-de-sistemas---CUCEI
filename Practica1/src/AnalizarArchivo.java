/*
 * 
 */
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AnalizarArchivo.
 */
public class AnalizarArchivo {

	/** The archivo. */
	RandomAccessFile archivo = null;
    
    /** The interprete. */
    InterpretarLinea interprete;
    
    /** The direccion. */
    String direccion;
   

    /**
     * Instantiates a new analizar archivo.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    AnalizarArchivo() throws IOException{
    	
    	archivo = null;
        interprete = new InterpretarLinea();
        
    }
    
    /**
     * Abrir archivo.
     *
     * @param direccion donde se encuentra el archivo
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void abrirArchivo(File direccion) throws IOException{
    	this.direccion=direccion.getAbsolutePath(); 
    	archivo = new RandomAccessFile(direccion, "r");	   
    }// fin del metodo
    
    /**
     * Leer archivo.
     *
     * @param a donde se va mostrar el archivo
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void leerArchivo(JTextArea a)throws IOException{
    	String linea;
    	int contador=0;
    	while((linea=archivo.readLine())!=null){
    		contador++;
    		a.append(contador + "\t"+linea+"\n");
    	}	
    	archivo.seek(0);
  
    }
    
    /**
     * Analizar.
     *
     * @param ints Tabla para mostrar el archivo.inst
     * @param errores Tabla para mostrar el archivo.err
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    boolean analizar(DefaultTableModel ints, DefaultTableModel errores)throws IOException{
    	String linea;
    	int contador=0;
    	if(verificarArchivo())
    	{
    		interprete.crearArchivo(direccion,ints,errores);
    		while((linea=archivo.readLine())!=null){
        		contador++;
        		
        		if(interprete.analizarLinea(linea,contador)&&!interprete.validarEND()){
        			interprete.resultado( contador);
        		}
        		if(interprete.validarEND()){
        			interprete.resultado( contador);
        			break;
        		}
        	}
    		interprete.cerrarArchivo();
        	archivo.seek(0);
        	JOptionPane.showMessageDialog(null,"Archivo creado en: "+ direccion.replace(".asm", ".ints"));
        	return true;
    	}
    	
    	else{
    		JOptionPane.showMessageDialog(null,"Abrir primero algun archivo");
    		return false;
    	}
  
    	
    }
    
    /**
     * Verificar archivo.
     *
     * @return true, if successful
     */
    boolean verificarArchivo(){
    	if(archivo != null)return true;
    	else return false;
    }
    
    
}
