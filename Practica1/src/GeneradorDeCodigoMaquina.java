import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;

class GeneradorDeCodigoMaquina {

	String direccion;
	File temporal;
	FileReader fr = null;
    BufferedReader br = null;
    ArrayList<Linea> archivoinst = new ArrayList<Linea>();
	Tabop tabop;
    FileWriter fw;
    PrintWriter pw;
    TablaSimbolos ts;
    DefaultTableModel inst;
    Errores err;
    Integer diferencias;
    					//X		Y	SP	 PC
    static String[] rr= {"00","01","10","11"}; 
    						//	   1	  2		3	    4	   5	  6	     7		 8		
    static String[][] prepost = {{"0000","0001","0010","0011","0100","0101","0110","0111"},
    				             {"1111","1110","1101","1100","1011","1010","1001","1000"}};
    					// A    B    D
    static String[] aa = {"00","01","10"};
    
    
    
	GeneradorDeCodigoMaquina(String direccion, Tabop t, TablaSimbolos tabsim, DefaultTableModel inst, DefaultTableModel errores) throws IOException{
		this.direccion = direccion.replace(".asm", ".inst");
		this.inst = inst;
		diferencias = 0;
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
	
	
	public void generarCodigo() throws IOException{
		String linea;
		Boolean banderaError = new Boolean(true);
		Boolean banderaRecalculo = false;
		int i = 0;
		
		while((linea = br.readLine()) != null){
			if(i>1){
				banderaError = true;
				Linea nueva = new Linea(linea,tabop,ts);

			    if(nueva.regresarModo() != null){
			    	if(nueva.regresarModo().regresarModo().compareTo("INH") == 0 || nueva.regresarModo().regresarModo().compareTo("IMM") == 0)
			    		banderaError = generarINHoIMM(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("DIR") == 0)
				    	banderaError = generarDIR(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("EXT") == 0)
				    	banderaError = generarEXT(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("IMM8") == 0 || nueva.regresarModo().regresarModo().compareTo("IMM16") == 0)
				    	banderaError = generarIMM(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("IDX") == 0)
				    	banderaError = generarIDX(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("IDX1") == 0)
				    	banderaError = generarIDX1yIDX2(nueva,1,"0");
				    else if(nueva.regresarModo().regresarModo().compareTo("IDX2") == 0)
				    	banderaError = generarIDX1yIDX2(nueva,2,"1");
				    else if(nueva.regresarModo().regresarModo().compareTo("[IDX2]") == 0)
				    	banderaError = generarINDIRECTOIDX2(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("[D,IDX]") == 0)
				    	banderaError = generarDIDX(nueva);
				    else if(nueva.regresarModo().regresarModo().compareTo("REL8")==0 || nueva.regresarModo().regresarModo().compareTo("REL16")==0)
				    	banderaError = generarREL(nueva,Integer.parseInt(nueva.regresarContador(),16)+nueva.modo.regresarSumaTotal());
			    }
			    
			    else{
			    	switch(nueva.regresarDirectiva()){
			    	
			    	case 9:case 13:case 17: 
						banderaError = generarDirectivasConstantes(nueva,1);
						break;
					case 8:case 12:case 27: 
						banderaError = generarDirectivasConstantes(nueva,2);
						break;
					case 16:			    
						banderaError = generarFCC(nueva);					   
						break;
			    	}
			    }
			    if(banderaError){
			    	archivoinst.add(nueva);
			    	banderaRecalculo = true;
			    }
			  	
			    else continue;
			    
			}
			else i++;
		}
		
		if(banderaRecalculo)this.recalcularContadorDeLocalidades();
		this.resultado();
		this.cerrarArchivo();
		err.cerrarArchivo();
	}
	
	private Boolean generarFCC(Linea linea) {
		int estado=0;
		String codigomaquina="";
							// "  ASCII
		int automataFCC[][]= {{ 1,-1,-1}, //0
							  { 2, 1,-1}, //1
							  {-1,-1,-1}};//2
		
		for(char aux:linea.regresarOperando().toCharArray()){
				
			if(aux=='\"')							 estado = automataFCC[estado][0];
			else if((int)aux>=32 && (int)aux <= 127){estado = automataFCC[estado][1];
													 codigomaquina+=Integer.toHexString((int)aux).toUpperCase();}
			else								     estado = automataFCC[estado][2];
				
		}
		linea.asignarCodigoMaquina(codigomaquina);
		return true;
	}



	private Boolean generarDirectivasConstantes(Linea linea,Integer bytes) {
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(linea.regresarOperando());
			String codigomaquina = regresarDigitosNecesarios(nodecimal,bytes);
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
		}catch(Exception e){return false;}
	}


	public Boolean generarINHoIMM(Linea linea){
		linea.asignarCodigoMaquina(linea.regresarModo().regresarCodigoMaquina());
		return true;
	}
	
	public Boolean generarDIR(Linea linea){
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(linea.regresarOperando());
			String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
			codigomaquina +=regresarDigitosNecesarios(nodecimal,linea.regresarModo().regresarPorCalcular());
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
		}catch(Exception e){return false;}
	}
	
	public Boolean generarEXT(Linea linea){
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		try{
			
			Integer nodecimal = Automata.cambiarABaseDecimal(linea.regresarOperando());
			codigomaquina +=regresarDigitosNecesarios(nodecimal,linea.regresarModo().regresarPorCalcular());
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
			
		}catch(Exception e){
			String conloc = ts.regresarConloc(linea.regresarOperando(),linea.regresarNoLinea());
			if(conloc != null){
				codigomaquina += conloc;
				linea.asignarCodigoMaquina(codigomaquina);
				return true;
			}	
			else {
				err.resultado(0, 2, linea.nolinea);
				desaparecerLinea(linea);
				return false;
			}
			
		}
	}
	
	public Boolean generarIMM(Linea linea){
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(linea.regresarOperando().substring(1));
			String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
			codigomaquina +=regresarDigitosNecesarios(nodecimal,linea.regresarModo().regresarPorCalcular());
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
		}catch(Exception e){return false;}
	}
	
	public Boolean generarIDX(Linea linea){
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		StringTokenizer idx;
		String acumulador;
		String registro;
		String nohexa = null;

		if(linea.operando.matches("[A|B|C|D][,][A-Za-z]+")){
			String xb = "111rr1aa";
			idx = new StringTokenizer(linea.operando,",");
			acumulador = this.numeroAcumulador(idx.nextToken());
			registro = this.numeroDeRegistro(idx.nextToken());
			
			xb = xb.replace("rr", registro);
			xb = xb.replace("aa", acumulador);
			xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
			codigomaquina+=xb;
			linea.asignarCodigoMaquina(codigomaquina);
			return true;	
		}
		
		else if(linea.operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]*[,][A-Za-z]+")){
			
			String xb = "rr0nnnnn";
			if(linea.operando.charAt(0)==','){
				xb = xb.replace("nnnnn", "00000");
				xb = xb.replace("rr", this.numeroDeRegistro(linea.operando.substring(1)));
			}
			else{
				idx = new StringTokenizer(linea.operando,",");
				try {
					nohexa = this.regresarDigitoBinariosNecesarios(Automata.cambiarABaseDecimal(idx.nextToken()),5);
				} catch (Exception e){
					e.printStackTrace();
				}
				registro = numeroDeRegistro(idx.nextToken());
				xb = xb.replace("rr", registro);
				xb = xb.replace("nnnnn", nohexa);
				
			}	
			xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
			codigomaquina+=xb;
			linea.asignarCodigoMaquina(codigomaquina);
			return true;

		}
		
		else if(linea.operando.matches("[$]?[@]?[%]?[-]?[A-Z0-9]+[,][+|-][A-Za-z]+")){
			
			String xb = "rr10nnnn";
			idx = new StringTokenizer(linea.operando,",");
			Integer nodecimal = null;
			try {
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
			} catch (Exception e) {
				e.printStackTrace();
			}
			registro = idx.nextToken();
			registro = this.numeroDeRegistro(registro.substring(1));
			
			if(registro.charAt(0) == '+')xb = xb.replace("nnnn", prepost[0][nodecimal-1]);
			else xb = xb.replace("nnnn", prepost[1][nodecimal-1]);
			
			xb = xb.replace("rr", registro);
			xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
			codigomaquina+=xb;
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
	

		}// fin del pre
		
		else if(linea.operando.matches("[$]?[@]?[%]?[-]?[A-Z0-9]+[,][A-Za-z]+[+|-]")){
			String xb = "rr11nnnn";
			idx = new StringTokenizer(linea.operando,",");
			Integer nodecimal = null;
			try {
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
			} catch (Exception e) {
				e.printStackTrace();
			}
			registro = idx.nextToken();
			registro = this.numeroDeRegistro(registro.substring(0,registro.length()-1));
			
			if(registro.endsWith("+"))xb = xb.replace("nnnn", prepost[0][nodecimal-1]);
			else xb = xb.replace("nnnn", prepost[1][nodecimal-1]);
			
			xb = xb.replace("rr", registro);
			xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
			codigomaquina+=xb;
			linea.asignarCodigoMaquina(codigomaquina);
			return true;
	
		}// fin del post
		
		else return false;
	}
	
	public Boolean generarIDX1yIDX2(Linea linea,Integer bytes,String z){
		
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		StringTokenizer idx;
		String registro;
		String xb = "111rr0zs";
		Integer decimal = null;
		
		xb = xb.replace("z",z);
		idx = new StringTokenizer(linea.operando,",");
		try {
			decimal = Automata.cambiarABaseDecimal(idx.nextToken());
		} catch (Exception e){
			e.printStackTrace();
		}
		registro = numeroDeRegistro(idx.nextToken());
		xb = xb.replace("rr", registro);
		if(decimal >= 0) xb = xb.replace("s", "0");
		else xb = xb.replace("s", "1");
		
		xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
		codigomaquina= codigomaquina + xb + this.regresarDigitosNecesarios(decimal, bytes);
		linea.asignarCodigoMaquina(codigomaquina);
		return true;

	}
	
	public Boolean generarINDIRECTOIDX2(Linea linea){
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		StringTokenizer idx;
		String registro;
		String xb = "111rr011";
		String auxoper = linea.operando.substring(1, linea.operando.length()-1);
		Integer decimal = null;
		
		idx = new StringTokenizer(auxoper,",");
		try {
			decimal = Automata.cambiarABaseDecimal(idx.nextToken());
		} catch (Exception e){
			e.printStackTrace();
		}
		registro = numeroDeRegistro(idx.nextToken());
		xb = xb.replace("rr", registro);
		
		xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
		codigomaquina= codigomaquina + xb + this.regresarDigitosNecesarios(decimal, 2);
		linea.asignarCodigoMaquina(codigomaquina);
		return true;
	}
	
	public Boolean generarDIDX(Linea linea){
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		StringTokenizer idx;
		String registro;
		String xb = "111rr111";
		String auxoper = linea.operando.substring(1, linea.operando.length()-1);
		
		idx = new StringTokenizer(auxoper,",");
		registro = idx.nextToken();
		registro = numeroDeRegistro(idx.nextToken());
		xb = xb.replace("rr", registro);
		 	
		xb = this.regresarDigitosNecesarios(Integer.parseInt(xb,2),1);
		codigomaquina= codigomaquina + xb;
		linea.asignarCodigoMaquina(codigomaquina);
		return true;
	}
	
	public Boolean generarREL(Linea linea,Integer conlocSiguiente){
		String codigomaquina = linea.regresarModo().regresarCodigoMaquina();
		Integer salto;
		try{
			Integer nodecimal = Automata.cambiarABaseDecimal(linea.regresarOperando());
			salto = nodecimal - conlocSiguiente;
			
		}catch(Exception e){
			String conloc = ts.regresarConloc(linea.regresarOperando(),linea.regresarNoLinea());
			if(conloc != null){
				salto = Integer.parseInt(conloc,16) - conlocSiguiente;
			}	
			else {
				err.resultado(0, 2, linea.nolinea);
				desaparecerLinea(linea);
				return false;
			}
			
		}
		
		if(linea.modo.regresarModo().compareTo("REL8") == 0){
			if(Automata.validarNumero(salto, 2, 128, -127)){
				codigomaquina +=regresarDigitosNecesarios(salto,linea.modo.regresarPorCalcular());
			}
			else{
				err.resultado(12, 0, linea.nolinea);
				desaparecerLinea(linea);
				return false;
			}
		}
		
		else{
			if(Automata.validarNumero(salto, 2,32767, -32768)){
				codigomaquina +=regresarDigitosNecesarios(salto,linea.modo.regresarPorCalcular());
			}
			else{
				err.resultado(12, 0, linea.nolinea);
				desaparecerLinea(linea);
				return false;
			}
		}
		
		linea.asignarCodigoMaquina(codigomaquina);
		return true;
	}

	
	public void resultado() {
		
		Iterator<Linea> iterador = archivoinst.iterator();
		
		while(iterador.hasNext()){
			Linea aux = iterador.next();
			Object[] fila = new Object[7];
			
			fila[0]=aux.nolinea; 
			fila[1]=aux.contador.toUpperCase();
			
			if(aux.etiqueta != null)fila[2]=aux.etiqueta.regresarNombre();
			else fila[2] = null;
			
			if(aux.codop != null)fila[3]=aux.codop.regresarInstruccion();
			else fila[3]=aux.directiva.regresarNombreDirectiva();
			
			fila[4]=aux.operando;
			
			if(aux.modo != null)fila[5]=aux.modo.regresarModo();
			else fila[5]= null;
			
			if(aux.codigomaquina != null)fila[6]=aux.codigomaquina.toUpperCase();
			else fila[6]= null;
			
			inst.addRow(fila);
			pw.println(String.format("%-8s  %-10s  %-10s  %-10s  %-20s %-10s %s",fila));
		
		}
				
	}
	
	public String regresarDigitosNecesarios(Integer decimal, Integer bytes){
		
		String hexa = Integer.toHexString(decimal);
		if(hexa.length() > bytes * 2){
			return hexa.substring(hexa.length() - bytes *2);
		}
		else{
			while(hexa.length() < bytes *2){
				hexa = '0' + hexa;
			}
			return hexa;
		}			
	}
	
	public String regresarDigitoBinariosNecesarios(Integer decimal, Integer bytes){
		
		String bin = Integer.toBinaryString(decimal);
		if(bin.length() > bytes){
			return bin.substring(bin.length() - bytes);
		}
		else{
			while(bin.length() < bytes){
				bin = '0' + bin;
			}
			return bin;
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
	
	public void desaparecerLinea(Linea linea){
		Etiqueta e = linea.regresarEtiqueta();
		Linea aux;
		if(e != null){
			ArrayList<Integer> listaLineas = e.regresarOperandos();
			
			for(int i=0;i < archivoinst.size();i++){
				aux = archivoinst.get(i);
				if(!listaLineas.isEmpty() && aux.regresarNoLinea().compareTo(listaLineas.get(0))==0){
					desaparecerLinea(aux);
					archivoinst.remove(aux);
					err.DescripcionError(aux.regresarOriginal());			
					listaLineas.remove(0);
					i-=2;
				}		
			}
			ts.eliminarEtiqueta(linea.etiqueta);
			ts.sobreescribirTDS();
		}
		
	}
	
	
	void recalcularContadorDeLocalidades(){
		Iterator<Linea> iterador = archivoinst.iterator();
		ContadorDeLocalidades c = new ContadorDeLocalidades();
		while(iterador.hasNext()){
			Linea aux = iterador.next();
			
			if(aux.codop != null){
				aux.contador = c.regresarContadorORGHexa();
				c.bytesPorIncrementar(aux.modo.regresarSumaTotal());
			}
			else{
				if(aux.directiva.regresarDirectiva() != 29){
					Automata.analizar(aux.operando, err, aux.directiva, 0, c);
				}
				
				if(c.banderaEQU(aux.directiva.regresarDirectiva())){
					try {
						c.asignarContadorEQU(Automata.cambiarABaseDecimal(aux.operando));
						aux.contador = c.regresarContadorEQUHexa();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					aux.contador = c.regresarContadorORGHexa();
				}
				
			}
			aux.actualizarEtiqueta();
			c.incrementarContador();
		}

		recalcularSaltos();
		ts.sobreescribirTDS();
	}	
	void recalcularSaltos(){
		Iterator<Linea> iterador = archivoinst.iterator();
		Linea aux;
		
		while(iterador.hasNext()){
			aux = iterador.next();
			if(aux.codop != null){			
				if(aux.regresarModo().regresarModo().compareTo("REL8")==0 || aux.regresarModo().regresarModo().compareTo("REL16")==0)
					generarREL(aux,Integer.parseInt(aux.regresarContador(),16)+aux.modo.regresarSumaTotal());
			}
			
		}
	}

	
	String numeroDeRegistro(String registro){
		if(registro.equalsIgnoreCase("X"))return rr[0];
		else if(registro.equalsIgnoreCase("Y"))return rr[1];
		else if(registro.equalsIgnoreCase("SP"))return rr[2];
		else if(registro.equalsIgnoreCase("PC"))return rr[3];
		else return null;
	}
	
	String numeroAcumulador(String acumulador){
		if(acumulador.equalsIgnoreCase("A"))return aa[0];
		else if(acumulador.equalsIgnoreCase("B"))return aa[1];
		else if(acumulador.equalsIgnoreCase("D"))return aa[2];
		else return null;
	}
	
}
