import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


import javax.swing.table.DefaultTableModel;


class TablaSimbolos {
	
	/** Lista de etiquetas*/
	ArrayList <String> listaEtiquetas;
	ArrayList <String> contador;
	
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
			contador = new ArrayList<String>();
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
		contador.add(conloc);
		pw.println(String.format("%-8s  %s",fila[0],fila[1]));
	}
    
    Boolean validarEtiqueta(String etiqueta){
    	if(listaEtiquetas.indexOf(etiqueta)==-1)return true;
    	else return false;
    }
    
    String regresarConloc(String etiqueta){
    	if(listaEtiquetas.indexOf(etiqueta) == -1)return null;
    	else return contador.get(listaEtiquetas.indexOf(etiqueta));
    }
    
    public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	}
    
    public void mostrarTabsim(){
    	Iterator<String> i =  listaEtiquetas.iterator();
    	Iterator<String> j =  contador.iterator();
    	
    	while(i.hasNext() && j.hasNext()){
    		System.out.print(i.next() + "  ");
    		System.out.println(j.next());
    	}
    }
	
}
