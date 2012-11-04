import java.util.ArrayList;


class Etiqueta {

	String nombre;
	String conloc;
	ArrayList <Integer> operandos;
	
	Etiqueta(String nombre, String conloc){
		this.nombre = nombre;
		this.conloc = conloc;
		operandos = new ArrayList <Integer> ();
	}
	
	String regresarConloc(){
		return conloc;
	}
	
	String regresarNombre(){
		return nombre;
	}
	
	void actualizarConloc(String conloc){
		this.conloc = conloc;
	}
	
	void agregarOperando(Integer linea){
		operandos.add(linea);
	}
	
	ArrayList<Integer> regresarOperandos(){
		return operandos;
	}
}
