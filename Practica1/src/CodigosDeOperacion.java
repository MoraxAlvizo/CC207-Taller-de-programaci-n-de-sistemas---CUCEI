import java.util.StringTokenizer;


public class CodigosDeOperacion {
	
	String instruccion;
	String modoDireccionamiento;
	String codigoMaquina;
	Integer calculados;
	Integer porCalcular;
	Integer sumaTotal;
	
	CodigosDeOperacion(String linea){
		
		StringTokenizer separador = new StringTokenizer(linea,"|"); 
		instruccion = separador.nextToken();
		modoDireccionamiento = separador.nextToken();
		codigoMaquina = separador.nextToken();
		calculados = Integer.parseInt(separador.nextToken());
		porCalcular = Integer.parseInt(separador.nextToken());
		sumaTotal = Integer.parseInt(separador.nextToken());
		
	}
	
	void mostrarCodigo(){
		System.out.println(instruccion+modoDireccionamiento+codigoMaquina+calculados+porCalcular+sumaTotal);
	}

}
