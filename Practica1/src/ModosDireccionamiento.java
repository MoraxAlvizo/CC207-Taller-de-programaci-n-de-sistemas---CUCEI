

public class ModosDireccionamiento {
	
	String modoDireccionamiento;
	String codigoMaquina;
	Integer calculados;
	Integer porCalcular;
	Integer sumaTotal;
	
	void insertarModo(String modo){
		modoDireccionamiento = modo;
	}
	
	void insertarCodigoMaquina(String codigoMaquina){
		this.codigoMaquina = codigoMaquina;
	}
	
	void insertarCalculados(Integer calculados){
		this.calculados = calculados;
	}
	
	void insertarPorCalcular(Integer porCalcular){
		this.porCalcular = porCalcular;
	}
	void insertarSumaTotal(Integer sumaTotal){
		this.sumaTotal = sumaTotal;
	}
	
	void mostrarModo(){
		System.out.println(modoDireccionamiento+"   "+codigoMaquina+"   "+calculados+"   "+porCalcular+"   "+sumaTotal);
		
	}
	
	String regresarModo(){
		return modoDireccionamiento;
	}
	
	Integer regresarPorCalcular(){
		return porCalcular;
	}

}
