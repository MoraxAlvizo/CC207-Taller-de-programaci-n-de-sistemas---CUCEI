
public class Registro {
	
	String tiporegistro;
	String longitud;
	String direccion;
	String codigomaquina;
	String checksum;
	
	Registro(String tipo){
		this.tiporegistro = tipo;
	}
	
	Registro(String tipo, String nombre){
		
		this.tiporegistro = tipo;
		this.codigomaquina = generarCodigoObjetoS0(nombre);
		this.direccion = "0000";
		this.longitud = GeneradorDeCodigoMaquina.regresarDigitosNecesarios((codigomaquina.length()+6)/2,1);
		this.generarChecksum();
	}
	
	public String generarCodigoObjetoS0(String nombre){
		
		StringBuilder s0 = new StringBuilder();
		for(char c:nombre.toCharArray()){
			s0.append(this.regresarPar(c));
		}
		return s0.toString();
	}
	
	public void asignarDireccion(String direccion){
		this.direccion = direccion;
	}
	
	public void asignarChecksum(String checksum){
		this.checksum = checksum;
	}
	
	public String generarCodigoObjetoS1(String codigomaquina){
		
		this.codigomaquina = codigomaquina;
		this.longitud = GeneradorDeCodigoMaquina.regresarDigitosNecesarios((codigomaquina.length()+6)/2,1);
		this.generarChecksum();
		return null;
		
	}
	
	
	public String regresarPar(char c){
		String hex = Integer.toHexString((int)c);
		if(hex.length()<2){
			return "0"+hex;
		}
		else return hex;
	}

	/*El campo del checksum se calcula sumando los valores representados por pares 
	 * (incluyendo los valores de la longitud, la dirección,
	 * el código y los datos) a la suma se le calcula el complemento a 
	 * uno y se representa el resultado tomando el byte más significativo
			del complemento. */
	public String generarChecksum(){
		
		String auxcheck = this.direccion + this.longitud + this.codigomaquina;
		Integer checksumdecimal = 0;
		int i=0,j=2;
		
		
		while(j<=auxcheck.length()){

			checksumdecimal += Integer.parseInt(auxcheck.substring(i, j), 16);
			i+=2;
			j+=2;
		}
		
		checksumdecimal = ~checksumdecimal;
		this.checksum = Integer.toHexString(checksumdecimal);
		checksumdecimal = this.checksum.length();
		this.checksum = this.checksum.substring(checksumdecimal-2, checksumdecimal);
		
		
		return null;
	}
	
	public Object[] regresarArreglo(){
		Object[] fila = new Object[7];
		
		fila[0]=this.tiporegistro.toUpperCase();
		fila[1]=this.longitud.toUpperCase();
		fila[2]=this.direccion.toUpperCase();
		
		if(!this.codigomaquina.isEmpty())fila[3]=this.codigomaquina.toUpperCase();
		else fila[3]= "";
		
		fila[4]=this.checksum.toUpperCase();
		
		return fila;
	}
	
	public String complementoA1(){
		return null;
	}
	
	public String toString(){
		return String.format("%-2s %-2s %-4s %-35s %-2s",this.tiporegistro, this.longitud, this.direccion, this.codigomaquina, this.checksum );
	}
}
