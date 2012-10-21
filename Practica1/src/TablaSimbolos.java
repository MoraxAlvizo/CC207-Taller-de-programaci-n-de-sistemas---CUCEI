import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javax.swing.table.DefaultTableModel;


class TablaSimbolos {
	
	/** Lista de etiquetas*/
	ArrayList <String> listaEtiquetas;
	
	/** El file nam. direccion del archivo */
    String fileNam;
    
    /** El fw.*/
    FileWriter fw;
    
    /** El pw. */
    PrintWriter pw;
    
    /** El ints. Tabla el manejo del archivo .tabsim*/
    DefaultTableModel tabsim;
    
    TablaSimbolos(DefaultTableModel tabsim){
    	this.tabsim = tabsim;
    }
    
    void crearArchivo(String direccion){
		try {
			listaEtiquetas = new ArrayList<String>();
			direccion = direccion.replace(".asm", ".tds");
	        fw = new FileWriter(direccion, false);
	        pw = new PrintWriter(fw);
	        pw.println(String.format("%-8s  %s","ETIQUETA","VALOR"));
	        pw.println(".........................");
	      
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
    
    public void resultado(String etiqueta, String conloc) {
		Object[] fila = new Object[2];
		fila[0]=etiqueta;
		fila[1]=conloc;
		tabsim.addRow(fila);
		listaEtiquetas.add(etiqueta);
		pw.println(String.format("%-8s  %s",fila[0],fila[1]));
		
	}
    
    Boolean validarEtiqueta(String etiqueta){
    	if(listaEtiquetas.indexOf(etiqueta)==-1)return true;
    	else return false;
    }
    
    public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	}
	
}
