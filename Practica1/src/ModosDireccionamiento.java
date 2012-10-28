

// TODO: Auto-generated Javadoc
/**
 * La Clase ModosDireccionamiento. Clase que almacena un modo de direccionamiento
 */
public class ModosDireccionamiento {
	
	/** EL modo direccionamiento.*/
	String modoDireccionamiento;
	
	/** El codigo maquina. */
	String codigoMaquina;
	
	/** Los calculados. */
	Integer calculados;
	
	/** Los por calcular. */
	Integer porCalcular;
	
	/** La suma total. */
	Integer sumaTotal;
	
	ModosDireccionamiento(){}
	ModosDireccionamiento(String error){
		modoDireccionamiento = error;
	}
	
	/**
	 * Insertar modo. Metodo para asignarle el nombre al modo de direccionamiento
	 *
	 * @param modo the modo
	 */
	void insertarModo(String modo){
		modoDireccionamiento = modo;
	}
	
	/**
	 * Insertar codigo maquina. Metodo para asignarle el codigo maquina
	 *
	 * @param codigoMaquina
	 */
	void insertarCodigoMaquina(String codigoMaquina){
		this.codigoMaquina = codigoMaquina;
	}
	
	/**
	 * Insertar calculados. Metodo para asignarle los bytes calculados
	 *
	 * @param calculados
	 */
	void insertarCalculados(Integer calculados){
		this.calculados = calculados;
	}
	
	/**
	 * Insertar por calcular.  Metodo para asignarle los bytes que faltan por calcular
	 *
	 * @param porCalcular
	 */
	void insertarPorCalcular(Integer porCalcular){
		this.porCalcular = porCalcular;
	}
	
	/**
	 * Insertar suma total. Metodo para asignarle la suma de bytes
	 *
	 * @param sumaTotal
	 */
	void insertarSumaTotal(Integer sumaTotal){
		this.sumaTotal = sumaTotal;
	}
	
	/**
	 * Mostrar modo. Muestra el modo en consola
	 */
	void mostrarModo(){
		System.out.println(modoDireccionamiento+"   "+codigoMaquina+"   "+calculados+"   "+porCalcular+"   "+sumaTotal);
		
	}
	
	/**
	 * Regresar modo. regresa el nombre del modo de direccionamiento 
	 *
	 * @return el modo de direccinamiento
	 */
	String regresarModo(){
		return modoDireccionamiento;
	}
	
	/**
	 * Regresar por calcular. regresa los bytes que faltan por calcular
	 *
	 * @return los bytes por calcular
	 */
	Integer regresarPorCalcular(){
		return porCalcular;
	}
	
	Integer regresarSumaTotal(){
		return sumaTotal;
	}
	
	String regresarCodigoMaquina(){
		return codigoMaquina;
	}

}
