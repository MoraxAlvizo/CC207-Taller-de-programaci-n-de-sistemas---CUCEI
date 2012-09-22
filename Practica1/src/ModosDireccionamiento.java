

// TODO: Auto-generated Javadoc
/**
 * The Class ModosDireccionamiento.
 */
public class ModosDireccionamiento {
	
	/** The modo direccionamiento. */
	String modoDireccionamiento;
	
	/** The codigo maquina. */
	String codigoMaquina;
	
	/** The calculados. */
	Integer calculados;
	
	/** The por calcular. */
	Integer porCalcular;
	
	/** The suma total. */
	Integer sumaTotal;
	
	/**
	 * Insertar modo.
	 *
	 * @param modo the modo
	 */
	void insertarModo(String modo){
		modoDireccionamiento = modo;
	}
	
	/**
	 * Insertar codigo maquina.
	 *
	 * @param codigoMaquina
	 */
	void insertarCodigoMaquina(String codigoMaquina){
		this.codigoMaquina = codigoMaquina;
	}
	
	/**
	 * Insertar calculados.
	 *
	 * @param calculados
	 */
	void insertarCalculados(Integer calculados){
		this.calculados = calculados;
	}
	
	/**
	 * Insertar por calcular.
	 *
	 * @param porCalcular
	 */
	void insertarPorCalcular(Integer porCalcular){
		this.porCalcular = porCalcular;
	}
	
	/**
	 * Insertar suma total.
	 *
	 * @param sumaTotal
	 */
	void insertarSumaTotal(Integer sumaTotal){
		this.sumaTotal = sumaTotal;
	}
	
	/**
	 * Mostrar modo.
	 */
	void mostrarModo(){
		System.out.println(modoDireccionamiento+"   "+codigoMaquina+"   "+calculados+"   "+porCalcular+"   "+sumaTotal);
		
	}
	
	/**
	 * Regresar modo.
	 *
	 * @return el modo de direccinamiento
	 */
	String regresarModo(){
		return modoDireccionamiento;
	}
	
	/**
	 * Regresar por calcular.
	 *
	 * @return los bytes por calcular
	 */
	Integer regresarPorCalcular(){
		return porCalcular;
	}

}
