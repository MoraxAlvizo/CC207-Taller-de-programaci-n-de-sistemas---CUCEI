import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


import javax.swing.table.DefaultTableModel;


class TablaSimbolos {
	
	/** Lista de etiquetas*/
	ArrayList <Etiqueta> listaEtiquetas;
	
	/** El file nam. direccion del archivo */
    String fileNam;
    
    /** El fw.*/
    FileWriter fw;
    
    /** El pw. */
    PrintWriter pw;
    
    /** El ints. Tabla el manejo del archivo .tabsim*/
    DefaultTableModel tabsim;
    
    String direccion;
    
    TablaSimbolos(DefaultTableModel tabsim){
    	this.tabsim = tabsim;
    }
    
    void crearArchivo(String direccion){
		try {
			listaEtiquetas = new ArrayList<Etiqueta>();
			this.direccion = direccion.replace(".asm", ".tds");
	        fw = new FileWriter(this.direccion, false);
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
		listaEtiquetas.add(new Etiqueta(etiqueta,conloc));
		pw.println(String.format("%-8s  %s",fila[0],fila[1]));
	}
    
    Boolean validarEtiqueta(String etiqueta){
    	if(listaEtiquetas.indexOf(etiqueta)==-1)return true;
    	else return false;
    }
    
    Etiqueta regresarEtiqueta(String etiqueta){
    	
    	if(etiqueta.compareToIgnoreCase("NULL")==0)return null;
    	else{
    		for(Etiqueta aux : listaEtiquetas){
        		if(aux.regresarNombre().equals(etiqueta))
        			return aux;
        	}
        	return null;
    	}
    	
    }
    
    String regresarConloc(String etiqueta, Integer nolinea){
    	for(Etiqueta aux : listaEtiquetas){
    		if(aux.regresarNombre().equals(etiqueta)){
    			aux.agregarOperando(nolinea);
    			return aux.regresarConloc();
    		}	
    	}
    	return null;
    }
    
    public void cerrarArchivo() throws IOException{
	    pw.close();
	    fw.close();
	}
    
    public void mostrarTabsim(){
    	Iterator<Etiqueta> i =  listaEtiquetas.iterator();
    	
    	while(i.hasNext()){
    		System.out.print(i.next().toString());
    	}
    }
    
    public void eliminarEtiqueta(Etiqueta etiqueta){
    	int linea;
    	if((linea = listaEtiquetas.indexOf(etiqueta)) != -1){
    		tabsim.removeRow(linea);
    		listaEtiquetas.remove(linea);
    	}
    }
    
    public void sobreescribirTDS(){
    	Object[] fila = new Object[2];
		
        try {
			fw = new FileWriter(direccion, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
        pw = new PrintWriter(fw);
        pw.println(String.format("%-8s  %s","ETIQUETA","VALOR"));
        pw.println("........................:...........");
        Iterator<Etiqueta> i =  listaEtiquetas.iterator();	
        vaciarTS();
    	while(i.hasNext()){
    		Etiqueta aux = i.next();
       		fila[0]=aux.nombre;
    		fila[1]=aux.conloc;
       		tabsim.addRow(fila);
    		pw.println(String.format("%-8s  %s",aux.nombre,aux.conloc));
    	}
    		
        
    	try {
			cerrarArchivo();
		} catch (IOException e) {}
    }
	
    void vaciarTS(){
    	while(tabsim.getRowCount() != 0){
    		tabsim.removeRow(0);
    	}
    }
}
