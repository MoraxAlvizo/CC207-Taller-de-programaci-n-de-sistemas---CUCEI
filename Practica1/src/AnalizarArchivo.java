/*
 * 
 */
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
/**
 * La Clase AnalizarArchivo. Clase que se dedica a analizar todo el archivo asm
 */
public class AnalizarArchivo {

    /** El interprete. Atributo que analizara linea por linea */
    InterpretarLinea interprete;
    
    /** El direccion. nombre de la direccion */
    String direccion;
    
    Boolean banderaError;
    
    File archivo;
    FileReader fr;
    BufferedReader br;
   
    
    /**
     * Instantiates a new analizar archivo.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    AnalizarArchivo() throws IOException{
    	
    	archivo = null;
        interprete = new InterpretarLinea();
        banderaError = false;
        
    }
    
    /**
     * Abrir archivo. Metodo que se encarga de abrir el archivo asm
     *
     * @param direccion donde se encuentra el archivo
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void abrirArchivo(File direccion) throws IOException{
    	this.direccion=direccion.getAbsolutePath();
    	archivo = direccion;
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);	 
        
    }// fin del metodo
    
    /**
     * Leer archivo. Metodo en el cual se muestra el archivo asm en la interfaz grafica
     *
     * @param a donde se va mostrar el archivo
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void leerArchivo(JTextArea a)throws IOException{
    	String linea;
    	int contador=0;
    	while((linea=br.readLine())!=null){
    		contador++;
    		a.append(contador + "\t"+linea+"\n");
    	}	
    	br.close();
    }
    
    /**
     * Analizar. Metodo que analiza el archivo asm
     *
     * @param ints Tabla para mostrar el archivo.inst
     * @param errores Tabla para mostrar el archivo.err
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    boolean analizar(DefaultTableModel ints, DefaultTableModel errores,DefaultTableModel tabsim,DefaultTableModel s19)throws IOException{
    	String linea;
    	int contador=0;
    	boolean banderaEND = false;
    	Errores err;
    	archivo = new File(direccion);
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);
        
    	if(verificarArchivo())
    	{
    		interprete.crearArchivo(direccion,errores,tabsim);
    		while((linea=br.readLine())!=null){
        		contador++;
        		
        		if(interprete.analizarLinea(linea,contador)&&!(banderaEND=interprete.validarEND())){
        			interprete.resultado( contador);
        		}
        		else if(banderaEND){
        			interprete.resultado( contador);
        			break;
        		}
        		else continue;
        	}
    		
    		br.close();
    		interprete.cerrarArchivo(banderaEND);
    		err = interprete.regresarErrores();
    		err.cerrarArchivo();
    		if(!err.regresarBanderaError()){
    			GeneradorDeCodigoMaquina a = new GeneradorDeCodigoMaquina(direccion,interprete.regresarTabop(),interprete.regresarTabSim(), ints, errores);
        		a.generarCodigo(s19);
            	JOptionPane.showMessageDialog(null,"Archivo creado en: "+ direccion.replace(".asm", ".ints"));
    		}
    		else JOptionPane.showMessageDialog(null,"Archivo inst no creado por que tiene errores","ERROR", JOptionPane.ERROR_MESSAGE);
        	return true;
    	}
    	
    	else{
    		JOptionPane.showMessageDialog(null,"Abrir primero algun archivo");
    		return false;
    	}
  
    	
    }
    
    /**
     * Verificar archivo. Verifica si el archivo fue abierto con exito
     *
     * @return true, if successful
     */
    boolean verificarArchivo(){
    	if(archivo != null)return true;
    	else return false;
    }
    
    
    
    
}
