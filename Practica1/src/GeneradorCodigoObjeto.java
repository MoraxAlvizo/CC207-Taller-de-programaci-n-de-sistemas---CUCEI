import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;


class GeneradorCodigoObjeto {
	
	GeneradorDeCodigoMaquina archivoints;
	String codigomaquina;
    FileWriter fw;
    PrintWriter pw;
    String direccion;
    ArrayList<Registro> registros = new ArrayList<Registro>();
    DefaultTableModel s19;
	
	GeneradorCodigoObjeto(GeneradorDeCodigoMaquina ints, String direccion, DefaultTableModel s19){
		
		this.archivoints = ints;
		this.s19 = s19;
		File f = new File(direccion.replace(".inst", ".asm"));
		this.direccion = direccion.replace(".inst", ".s19"); 
		try {
			fw = new FileWriter(this.direccion, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Registro s0 = new Registro("S0",f.getName());
		registros.add(s0);
		s19.addRow(s0.regresarArreglo());
        pw = new PrintWriter(fw);
        pw.println(s0.toString());
       

	}
	
	public void generarCodigo(){
		
		Integer contador = 0;
		StringBuilder codigomaquina = new StringBuilder();
		String reserva = "";
		Registro s1 = new Registro("S1");
		ArrayList<Integer> deBloque =   new ArrayList<Integer>();	
		
		deBloque.add(19);
		deBloque.add(21);
		deBloque.add(25);
		deBloque.add(22);
		deBloque.add(26);
		Boolean banderaContador = false;
		
		for(Linea l:archivoints.regresarArrayListInst()){
			
			if(banderaContador){
				contador = Integer.parseInt(l.regresarContador(),16);
				s1.asignarDireccion(GeneradorDeCodigoMaquina.regresarDigitosNecesarios(contador, 2));
				banderaContador = false;
			}
			
			if(l.regresarDirectiva()!=-1){
				if(l.regresarDirectiva() == 3){
					s1 = new Registro("S1");
					contador = Integer.parseInt(l.regresarContador(),16);
					s1.asignarDireccion(GeneradorDeCodigoMaquina.regresarDigitosNecesarios(contador, 2));
				}
				else if(l.regresarCodigoMaquina()!=null){
					codigomaquina.append(l.regresarCodigoMaquina());
				}
				else if(deBloque.indexOf(l.regresarDirectiva())!=-1 && !codigomaquina.toString().isEmpty()){
					s1.generarCodigoObjetoS1(codigomaquina.toString());
					pw.println(s1.toString());
					s19.addRow(s1.regresarArreglo());
					contador = contador + (codigomaquina.toString().length()/2);
					s1 = new Registro("S1");
					banderaContador = true;
					codigomaquina = new StringBuilder();
				}
				
			}
			
			else{
				codigomaquina.append(l.regresarCodigoMaquina());

			}
			
			if(codigomaquina.toString().length()==32){
				
				s1.generarCodigoObjetoS1(codigomaquina.toString());
				pw.println(s1.toString());
				s19.addRow(s1.regresarArreglo());
				contador += 16;
				s1 = new Registro("S1");
				s1.asignarDireccion(GeneradorDeCodigoMaquina.regresarDigitosNecesarios(contador, 2));
				codigomaquina = new StringBuilder();
			}
			
			else if(codigomaquina.toString().length()>32){
				
				reserva = codigomaquina.toString().substring(32);
				s1.generarCodigoObjetoS1(codigomaquina.toString().substring(0,32));
				pw.println(s1.toString());
				s19.addRow(s1.regresarArreglo());
				contador += 16;
				s1 = new Registro("S1");
				s1.asignarDireccion(GeneradorDeCodigoMaquina.regresarDigitosNecesarios(contador, 2));
				
				
				while((reserva.length()/2)>=16){
					s1.generarCodigoObjetoS1(reserva.substring(0, 32));
					pw.println(s1.toString());
					s19.addRow(s1.regresarArreglo());
					contador += 16;
					s1 = new Registro("S1");
					s1.asignarDireccion(GeneradorDeCodigoMaquina.regresarDigitosNecesarios(contador, 2));
					
					codigomaquina = new StringBuilder();
					if((reserva.length()/2)>=19)
						reserva = reserva.substring(19*2);
					else continue;
					
				}
				codigomaquina = new StringBuilder(reserva);
			}
			
		}
		
		if(!codigomaquina.toString().isEmpty()){	
			s1.generarCodigoObjetoS1(codigomaquina.toString());
			pw.println(s1.toString());
			s19.addRow(s1.regresarArreglo());
		}
		
		s1 = new Registro("S9");
		s1.asignarDireccion("0000");
		s1.generarCodigoObjetoS1("");
		s1.asignarChecksum("FC");
		pw.println(s1.toString());
		s19.addRow(s1.regresarArreglo());
		
		 try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        pw.close();

	}
	

}
