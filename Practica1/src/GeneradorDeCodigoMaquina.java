import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

class GeneradorDeCodigoMaquina {

	String direccion;
	File temporal;
	FileReader fr = null;
    BufferedReader br = null;
    Integer nolinea;
    String contador;
    String etiqueta;
    CodigosDeOperacion codop;
    Directivas directiva;
    String operando;
    ModosDireccionamiento modo;
    String codigomaquina;
	Tabop tabop;
    FileWriter fw;
    PrintWriter pw;
    TablaSimbolos ts;
    DefaultTableModel inst;
    Errores err;
    
    
    
	GeneradorDeCodigoMaquina(String direccion, Tabop t, TablaSimbolos tabsim, DefaultTableModel inst, DefaultTableModel errores) throws IOException{
		this.direccion = direccion.replace(".asm", ".inst");
		this.inst = inst;
		ts = tabsim;
		ts.mostrarTabsim();
		temporal = new File("temporal.inst");
		fr = new FileReader(temporal);
		br = new BufferedReader(fr);
		tabop = t;
		err = new Errores(errores);
		err.crearArchivo(direccion);
		fw = new FileWriter(this.direccion, false);
        pw = new PrintWriter(fw);
        pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %-10s %s","LINEA","CONTLOC","ETIQUETA","CODOP","OPERANDO","MODO","CODIGO MAQUINA"));
        pw.println(".......................................................................................................");
	}
	
	private void inicializarLinea(){
		 nolinea = null;
		 contador = null;
		 etiqueta = null;
		 codop = null;
		 directiva = null;
		 operando = null;
		 modo = null;
		 codigomaquina = null;
	}
	
	
	public void generarCodigo() throws IOException{
		String linea;
		StringTokenizer separador;
		String cod;
		Boolean banderaError = new Boolean(true);
		int i = 0;
		while((linea = br.readLine()) != null){
			if(i>1){
				banderaError = true;
				inicializarLinea();	
				separador = new StringTokenizer(linea);
				nolinea = new Integer(separador.nextToken());
			    contador = separador.nextToken();
			    etiqueta =  separador.nextToken();
			    
			    cod = separador.nextToken();
			    directiva = new Directivas(cod);
			    if(directiva.regresarDirectiva() == -1)
			    	codop = tabop.busqueda(cod);
			    
			    operando = separador.nextToken();
			    if(directiva.regresarDirectiva() == -1)
			    	modo = codop.regresarModo(separador.nextToken());
			    else modo = null;
			    
			    if(modo != null){
			    	if(modo.regresarModo().compareTo("INH") == 0)
			    		banderaError = generarINH();
				    else if(modo.regresarModo().compareTo("DIR") == 0)
				    	banderaError = generarDIR();
				    else if(modo.regresarModo().compareTo("EXT") == 0)
				    	banderaError = generarEXT();
				    else if(modo.regresarModo().compareTo("IMM8") == 0 || modo.regresarModo().compareTo("IMM16") == 0)
				    	banderaError = generarIMM();
			    }
			    if(banderaError)
			    	resultado();
			    else continue;
			    
			}
			else i++;
		}
		
		cerrarArchivo();
	}
	
	public Boolean generarINH(){
		codigomaquina = modo.regresarCodigoMaquina();
		return true;
	}
	
	public Boolean generarDIR(){
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(operando);
			codigomaquina = modo.regresarCodigoMaquina();
			codigomaquina +=regresarDigitosNecesarios(nodecimal,modo.regresarPorCalcular());
			return true;
		}catch(Exception e){return false;}
	}
	
	public Boolean generarEXT(){
		try{
			codigomaquina = modo.regresarCodigoMaquina();
			Integer nodecimal = Automata.cambiarABaseDecimal(operando);
			codigomaquina +=regresarDigitosNecesarios(nodecimal,modo.regresarPorCalcular());
			return true;
			
		}catch(Exception e){
			String conloc = ts.regresarConloc(operando);
			if(conloc != null){
				codigomaquina += conloc;
				return true;
			}	
			else {
				err.resultado(0, 2, nolinea);
				return false;
			}
			
		}
	}
	
	public Boolean generarIMM(){
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(operando.substring(1));
			codigomaquina = modo.regresarCodigoMaquina();
			codigomaquina +=regresarDigitosNecesarios(nodecimal,modo.regresarPorCalcular());
			return true;
		}catch(Exception e){return false;}
	}
	
	public void resultado() {

		Object[] fila = new Object[7];
		fila[0]=nolinea; 
		fila[1]=contador;
		fila[2]=etiqueta;
		if(codop != null)fila[3]=codop.regresarInstruccion();
		else fila[3]=directiva.regresarNombreDirectiva();
		fila[4]=operando;
		if(modo != null)fila[5]=modo.regresarModo();
		else fila[5]= null;
		fila[6]=codigomaquina;
		
		inst.addRow(fila);
		pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %-10s %s",fila));
			
	}
	
	public String regresarDigitosNecesarios(Integer decimal, Integer bytes){
		
		String hexa = Integer.toHexString(decimal);
		if(hexa.length() > bytes * 2){
			return hexa.substring(hexa.length() - bytes * 2);
		}
		else{
			while(hexa.length() < bytes * 2){
				hexa = '0' + hexa;
			}
			return hexa;
		}			
	}
	
	public void cerrarArchivo(){
		pw.close();
	    try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
